package com.base.admin.infrastructure.adapter.in.rest.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.personal.PersonalService;
import com.base.admin.domain.entity.personal.Personal;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/personal")
public class PersonalController extends BaseEntityController<Personal> {

    PersonalController(@Autowired PersonalService entityService) {
        this.entityService = entityService;
        this.domainClass = Personal.class;
    }
}
