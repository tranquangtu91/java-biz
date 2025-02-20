package com.base.admin.repository.domain_mapping;

import org.springframework.stereotype.Repository;

import com.base.admin.entity.domain_mapping.DomainMapping;
import com.base.common.repository.BaseCrudRepository;

import java.util.List;

@Repository
public interface DomainMappingRepository extends BaseCrudRepository<DomainMapping, Long> {
    List<DomainMapping> findByFirstDomainAndFirstIdAndSecondDomainAndSecondId(String firstDomain, Long firstId,
            String secondDomain, Long secondId);
}
