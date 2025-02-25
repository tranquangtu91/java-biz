package com.base.admin.controller.domainmapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.domainmapping.DomainMapping;
import com.base.admin.service.domainmapping.impl.DomainMappingService;
import com.base.common.controller.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/domain-mapping")
public class DomainMappingController extends BaseEntityController<DomainMapping> {

    DomainMappingController(@Autowired DomainMappingService entityService) {
        this.entityService = entityService;
        this.domainClass = DomainMapping.class;
    }
}
