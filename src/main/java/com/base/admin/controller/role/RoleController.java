package com.base.admin.controller.role;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.entity.role.Role;
import com.base.admin.service.role.RoleService;
import com.base.common.controller.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/role")
public class RoleController extends BaseEntityController<Role> {

    public RoleController(@Autowired RoleService entityService) {
        this.entityService = entityService;
        this.domainClass = Role.class;

        updateExcludeFields = Arrays.asList("id", "uuid",
                "createdAt", "createdBy",
                "updatedBy", "updatedAt",
                "deleted",
                "authority");
    }
}
