package com.base.admin.application.port.out.repository.domainmapping;

import org.springframework.stereotype.Repository;

import com.base.admin.domain.entity.domainmapping.DomainMapping;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

import java.util.List;

@Repository
public interface DomainMappingRepository extends BaseCrudRepository<DomainMapping, Long> {
    List<DomainMapping> findByFirstDomainAndFirstIdAndSecondDomainAndSecondId(String firstDomain, Long firstId,
            String secondDomain, Long secondId);

    List<DomainMapping> findAllByFirstDomainAndFirstIdAndSecondDomain(String firstDomain, Long firstId,
            String secondDomain);
}
