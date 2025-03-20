package com.base.admin.infrastructure.adapter.in.rest.role.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.role.impl.RoleService;
import com.base.admin.domain.entity.role.Role;
import com.base.admin.infrastructure.adapter.in.rest.role.IRoleController;
import com.base.common.domain.dto.generalresponse.GeneralResponse;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/role")
public class RoleController extends BaseEntityController<Role> implements IRoleController {

    public RoleController(@Autowired RoleService entityService) {
        this.entityService = entityService;
        this.domainClass = Role.class;

        updateExcludeFields = Arrays.asList("id", "uuid",
                "createdAt", "createdBy",
                "updatedBy", "updatedAt",
                "deleted",
                "authority");
    }

    @Override
    public ResponseEntity<GeneralResponse> menuIds(Long id) {
        GeneralResponse gr = ((RoleService) entityService).menuIds(id);
        return new ResponseEntity<>(gr, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GeneralResponse> updateMenu(Long id, Set<Long> body) {
        GeneralResponse gr = ((RoleService) entityService).updateMenu(id, body);
        return new ResponseEntity<>(gr, HttpStatus.OK);
    }
}
