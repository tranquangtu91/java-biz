package com.base.admin.application.port.out.repository.menu;

import org.springframework.stereotype.Repository;

import com.base.admin.domain.entity.menu.Menu;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

@Repository
public interface MenuRepository extends BaseCrudRepository<Menu, Long> {

}
