package com.base.admin.controller.request_map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.request_map.RequestMap;
import com.base.admin.service.request_map.RequestMapService;
import com.base.common.controller.entity.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/request-map")
public class RequestMapController extends BaseEntityController<RequestMap> {

    RequestMapController(@Autowired RequestMapService entityService) {
        this.entityService = entityService;
        this.domainClass = RequestMap.class;
    }
}
