package com.base.admin.infrastructure.adapter.in.rest.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.menu.MenuService;
import com.base.admin.domain.entity.menu.Menu;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/menu")
public class MenuController extends BaseEntityController<Menu> {

    MenuController(@Autowired MenuService entityService) {
        this.entityService = entityService;
        this.domainClass = Menu.class;
    }
}
