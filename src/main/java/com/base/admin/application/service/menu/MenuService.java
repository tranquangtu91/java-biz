package com.base.admin.application.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.application.port.out.repository.menu.MenuRepository;
import com.base.admin.domain.entity.menu.Menu;
import com.base.common.application.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService extends BaseEntityService<Menu> {

    MenuService(@Autowired MenuRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
