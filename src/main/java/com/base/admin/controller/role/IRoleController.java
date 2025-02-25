package com.base.admin.controller.role;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.base.common.dto.generalresponse.GeneralResponse;

public interface IRoleController {
    @GetMapping(path = {"/{id:\\d+}/menu-ids"})
    ResponseEntity<GeneralResponse> menuIds(@PathVariable("id") Long id);

    @PutMapping(path = {"/{id:\\d+}/update-menu"})
    ResponseEntity<GeneralResponse> updateMenu(@PathVariable("id") Long id, @RequestBody Set<Long> body);
}
