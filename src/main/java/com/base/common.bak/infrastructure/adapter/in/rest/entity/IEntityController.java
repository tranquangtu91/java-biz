package com.base.common.infrastructure.adapter.in.rest.entity;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IEntityController {
    /**
     * Get all entities
     * @return
     */
     @GetMapping
    public ResponseEntity<Object[]> index();

    /**
     * Save a new entity
     * @param item
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Map<String, Object> item);

    /**
     * Get an entity by id
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Object> get(@PathVariable Long id);

    /**
     * Update an entity
     * @param id
     * @param item
     * @return
     */
    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/{id:\\d+}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Map<String, Object> item);

    /**
     * Delete an entity
     * @param id
     * @return
     */
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Object> delete(@PathVariable Long id);

    @GetMapping(path = { "/loadDataTable", "load-data-table" })
    public ResponseEntity<?> loadDataTableGet(@RequestParam Map<String, Object> params);

    @PostMapping(path = { "/loadDataTable", "load-data-table" })
    public ResponseEntity<?> loadDataTablePost(@RequestBody Map<String, Object> params);
}
