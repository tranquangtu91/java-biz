package com.base.admin.service.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.base.admin.entity.role.Role;
import com.base.admin.repository.role.RoleRepository;
import com.base.admin.utils.SpringSecurityService;
import com.base.common.service.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService extends BaseEntityService<Role> {
    public static final Integer MIN_ROLE_PRIORITY = 99999999;

    RoleService(@Autowired RoleRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    /**
     * Lấy danh sách role được phép thao tác theo Priority của user
     * 
     * @return
     */
    List<Role> listByPriority() {
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
}
