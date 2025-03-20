package com.base.admin.infrastructure.adapter.in.rest.domainmapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.domainmapping.impl.DomainMappingService;
import com.base.admin.domain.entity.domainmapping.DomainMapping;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/domain-mapping")
public class DomainMappingController extends BaseEntityController<DomainMapping> {

    DomainMappingController(@Autowired DomainMappingService entityService) {
        this.entityService = entityService;
        this.domainClass = DomainMapping.class;
    }
}
