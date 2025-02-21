package com.base.admin.controller.domain_mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.domain_mapping.DomainMapping;
import com.base.admin.service.domain_mapping.DomainMappingService;
import com.base.common.controller.entity.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/domain-mapping")
public class DomainMappingController extends BaseEntityController<DomainMapping> {

    DomainMappingController(@Autowired DomainMappingService entityService) {
        this.entityService = entityService;
        this.domainClass = DomainMapping.class;
    }
}
