package com.base.admin.controller.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.menu.Menu;
import com.base.admin.service.menu.MenuService;
import com.base.common.controller.entity.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/menu")
public class MenuController extends BaseEntityController<Menu> {

    MenuController(@Autowired MenuService entityService) {
        this.entityService = entityService;
        this.domainClass = Menu.class;
    }
}
