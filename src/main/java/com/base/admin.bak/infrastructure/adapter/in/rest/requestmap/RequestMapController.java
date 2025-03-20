package com.base.admin.infrastructure.adapter.in.rest.requestmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.requestmap.RequestMapService;
import com.base.admin.domain.entity.requestmap.RequestMap;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/request-map")
public class RequestMapController extends BaseEntityController<RequestMap> {

    RequestMapController(@Autowired RequestMapService entityService) {
        this.entityService = entityService;
        this.domainClass = RequestMap.class;
    }
}
