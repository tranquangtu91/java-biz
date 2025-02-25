package com.base.admin.repository.domainmapping;

import org.springframework.stereotype.Repository;

import com.base.admin.entity.domainmapping.DomainMapping;
import com.base.common.repository.BaseCrudRepository;

import java.util.List;

@Repository
public interface DomainMappingRepository extends BaseCrudRepository<DomainMapping, Long> {
    List<DomainMapping> findByFirstDomainAndFirstIdAndSecondDomainAndSecondId(String firstDomain, Long firstId,
            String secondDomain, Long secondId);

    List<DomainMapping> findAllByFirstDomainAndFirstIdAndSecondDomain(String firstDomain, Long firstId,
            String secondDomain);
}
