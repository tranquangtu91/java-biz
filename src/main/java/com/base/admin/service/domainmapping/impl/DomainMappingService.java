package com.base.admin.service.domainmapping.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.domainmapping.DomainMapping;
import com.base.admin.repository.domainmapping.DomainMappingRepository;
import com.base.admin.service.domainmapping.IDomainMappingService;
import com.base.common.dto.datatablefilter.DataTableFilter;
import com.base.common.dto.datatablefilter.Filter;
import com.base.common.dto.datatablefilter.LoadDataTableOptions;
import com.base.common.dto.datatableresponse.DataTableResponse;
import com.base.common.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class DomainMappingService extends BaseEntityService<DomainMapping> implements IDomainMappingService {
    DomainMappingRepository domainMappingRepository;

    DomainMappingService(@Autowired DomainMappingRepository entityRepository) {
        this.entityRepository = entityRepository;
        this.domainMappingRepository = entityRepository;
    }

    @Override
    public List<DomainMapping> find(String firstDomain, Long firstId, String secondDomain, Long secondId) {
        List<DomainMapping> domainMappings = domainMappingRepository
                .findByFirstDomainAndFirstIdAndSecondDomainAndSecondId(firstDomain, firstId, secondDomain, secondId);
        return domainMappings;
    }

    /**
     * Tạo 1 liên kết
     * 
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondId
     * @return
     */
    @Override
    public DomainMapping join(String firstDomain, Long firstId, String secondDomain, Long secondId) {
        List<DomainMapping> domainMappings = find(firstDomain, firstId, secondDomain, secondId);

        if (!domainMappings.isEmpty())
            return null;

        DomainMapping __item = new DomainMapping();
        __item.setFirstDomain(firstDomain);
        __item.setFirstId(firstId);
        __item.setSecondDomain(secondDomain);
        __item.setSecondId(secondId);
        __item = this.save(__item);

        return __item;
    }

    /**
     * Tạo nhiều liên kết
     * 
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondIds
     * @return
     */
    @Override
    public List<DomainMapping> joinMulti(String firstDomain, Long firstId, String secondDomain, Long[] secondIds) {
        DataTableFilter dtf = new DataTableFilter();
        Map<String, Filter> filters = new HashMap<>();
        filters.put("firstDomain", new Filter() {
            {
                setValue(firstDomain);
                setMatchMode(MatchMode.EQUALS);
            }
        });
        filters.put("firstId", new Filter() {
            {
                setValue(firstId);
                setMatchMode(MatchMode.EQUALS);
            }
        });
        filters.put("secondDomain", new Filter() {
            {
                setValue(secondDomain);
                setMatchMode(MatchMode.EQUALS);
            }
        });
        filters.put("secondId", new Filter() {
            {
                setValue(secondIds);
                setMatchMode(MatchMode.IN_LIST);
            }
        });
        dtf.setFilters(filters);
        LoadDataTableOptions options = new LoadDataTableOptions();
        options.domainClass = DomainMapping.class;

        DataTableResponse<DomainMapping> dtr = (DataTableResponse<DomainMapping>) loadDataTable(dtf, options);
        List<DomainMapping> domainMappings = dtr.items;

        List<String> existKeys = domainMappings.stream().map(it -> {
            return String.format("%s_%d_%s_%d", it.getFirstDomain(), it.getFirstId(), it.getSecondDomain(),
                    it.getSecondId());
        }).collect(Collectors.toList());

        List<DomainMapping> newDomainMappings = new ArrayList<>();

        for (Long secondId : secondIds) {
            String key = String.format("%s_%d_%s_%d", firstDomain, firstId, secondDomain, secondId);
            if (existKeys.contains(key))
                continue;
            DomainMapping domainMapping = join(firstDomain, firstId, secondDomain, secondId);
            newDomainMappings.add(domainMapping);
        }

        return newDomainMappings;
    }

    @Override
    public void smartJoin(String firstDomain, Long firstId, String secondDomain, Long[] secondIds) {
        List<DomainMapping> domainMappings = ((DomainMappingRepository) this.entityRepository).findAllByFirstDomainAndFirstIdAndSecondDomain(firstDomain, firstId, secondDomain);
        
        // Tạo liên kết mới
        List<String> existKeys = domainMappings.stream().map(it -> {
            return String.format("%s_%d_%s_%d", it.getFirstDomain(), it.getFirstId(), it.getSecondDomain(),
                    it.getSecondId());
        }).collect(Collectors.toList());
        for (Long secondId : secondIds) {
            String key = String.format("%s_%d_%s_%d", firstDomain, firstId, secondDomain, secondId);
            if (!existKeys.contains(key)) {
                log.info("Tạo liên kết mới: {} {} {} {}", firstDomain, firstId, secondDomain, secondId);
                join(firstDomain, firstId, secondDomain, secondId);
            }
        }

        // Xóa liên kết cũ
        List<String> setKeys = Arrays.stream(secondIds).map(it -> {
            return String.format("%s_%d_%s_%d", firstDomain, firstId, secondDomain, it);
        }).collect(Collectors.toList());
        for (DomainMapping domainMapping : domainMappings) {
            String key = String.format("%s_%d_%s_%d", domainMapping.getFirstDomain(), domainMapping.getFirstId(), domainMapping.getSecondDomain(), domainMapping.getSecondId());
            if (!setKeys.contains(key)) {
                log.info("Xóa liên kết: {} {} {} {}", domainMapping.getFirstDomain(), domainMapping.getFirstId(), domainMapping.getSecondDomain(), domainMapping.getSecondId());
                delete(domainMapping.getId());
            }
        }
    }

    @Override
    public void unJoin(String firstDomain, Long firstId, String secondDomain, Long secondId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unJoin'");
    }

    @Override
    public void unJoinMulti(String firstDomain, Long firstId, String secondDomain, Long[] secondIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unJoinMulti'");
    }
}
