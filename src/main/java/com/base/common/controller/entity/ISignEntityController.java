package com.base.common.controller.entity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ISignEntityController {
    @GetMapping("/:id(\\d+)/check-sign")
    public ResponseEntity<?> checkSign(@PathVariable Long id);
}
