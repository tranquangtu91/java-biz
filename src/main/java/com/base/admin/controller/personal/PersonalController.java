package com.base.admin.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.personal.Personal;
import com.base.admin.service.personal.PersonalService;
import com.base.common.controller.entity.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/personal")
public class PersonalController extends BaseEntityController<Personal> {

    PersonalController(@Autowired PersonalService entityService) {
        this.entityService = entityService;
        this.domainClass = Personal.class;
    }
}
