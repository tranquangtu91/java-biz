package com.base.admin.repository.menu;

import org.springframework.stereotype.Repository;

import com.base.admin.entity.menu.Menu;
import com.base.common.repository.BaseCrudRepository;

@Repository
public interface MenuRepository extends BaseCrudRepository<Menu, Long> {

}
