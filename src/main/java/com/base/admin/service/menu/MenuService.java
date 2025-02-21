package com.base.admin.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.menu.Menu;
import com.base.admin.repository.menu.MenuRepository;
import com.base.common.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService extends BaseEntityService<Menu> {

    MenuService(@Autowired MenuRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
