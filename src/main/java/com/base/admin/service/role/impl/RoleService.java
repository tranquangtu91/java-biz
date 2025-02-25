package com.base.admin.service.role.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.base.admin.entity.domainmapping.DomainMapping;
import com.base.admin.entity.role.Role;
import com.base.admin.repository.role.RoleRepository;
import com.base.admin.service.domainmapping.impl.DomainMappingService;
import com.base.admin.service.role.IRoleService;
import com.base.admin.utils.SpringSecurityService;
import com.base.common.dto.datatablefilter.DataTableFilter;
import com.base.common.dto.datatablefilter.Filter;
import com.base.common.dto.datatablefilter.LoadDataTableOptions;
import com.base.common.dto.datatablefilter.Filter.DataType;
import com.base.common.dto.datatablefilter.Filter.MatchMode;
import com.base.common.dto.datatableresponse.DataTableResponse;
import com.base.common.dto.generalresponse.GeneralResponse;
import com.base.common.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService extends BaseEntityService<Role> implements IRoleService {
    public static final Integer MIN_ROLE_PRIORITY = 99999999;

    @Autowired
    DomainMappingService domainMappingService;

    RoleService(@Autowired RoleRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public GeneralResponse menuIds(Long id) {
        DataTableFilter dataTableFilter = new DataTableFilter();
        Map<String, Filter> filters = new HashMap<>();
        filters.put("firstDomain", new Filter("firstDomain", MatchMode.EQUALS, "role", 0, DataType.string));
        filters.put("firstId", new Filter("firstId", MatchMode.EQUALS, id, 0, DataType.number));
        dataTableFilter.setFilters(filters);
        dataTableFilter.setRows(1000);

        DataTableResponse<?> dataTableResponse = domainMappingService.loadDataTable(dataTableFilter, new LoadDataTableOptions(null, null, DomainMapping.class));
        GeneralResponse gr = new GeneralResponse();
        gr.setValue(dataTableResponse.items.stream().map(it -> ((DomainMapping)it).getSecondId()).collect(Collectors.toSet()));
        return gr;
    }

    @Override
    public GeneralResponse updateMenu(Long id, Set<Long> menuIds) {
        domainMappingService.smartJoin("role", id, "menu", menuIds.toArray(new Long[0]));
        return new GeneralResponse();
    }

    /**
     * Lấy danh sách role được phép thao tác theo Priority của user
     * 
     * @return
     */
    @Override
    public List<Role> listByPriority() {
        UserDetails userDetails = SpringSecurityService.getPrincipal();
        Integer maxPriority = MIN_ROLE_PRIORITY;

        List<Role> roles = (List<Role>) this.entityRepository.findAll();
        for (Role role : roles) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(role.getAuthority()))) {
                if (role.getPriority() < maxPriority)
                    maxPriority = role.getPriority();
            }
        }

        final Integer __max_priority = maxPriority;
        roles = roles.stream().filter(it -> {
            return it.getPriority() >= __max_priority;
        }).collect(Collectors.toList());

        return roles;
    }

    @Override
    public GeneralResponse compareUserPriorityByUsername(String firstUsername, String secondaryUsername) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareUserPriorityByUsername'");
    }
}
