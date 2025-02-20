package com.base.common.controller.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.common.dto.data_table_filter.DataTableFilter;
import com.base.common.dto.data_table_response.DataTableResponse;
import com.base.common.dto.general_response.ResponseCode;
import com.base.common.service.IEntityService;
import com.base.common.service.LoadDataTableOptions;
import com.base.common.utils.convert.object.ObjectUtils;

@SuppressWarnings("unchecked")
public class BaseEntityController<T> {
    public IEntityService<T> entityService;
    public Class<?> domainClass;

    public List<String> getExcludeFields = new ArrayList<String>() {
        {
            Arrays.asList("updatedBy", "deleted");
        }
    };

    public List<String> saveExcludeFields = new ArrayList<String>() {
        {
            Arrays.asList("id", "uuid", "version",
                    "createdAt", "createdBy",
                    "updatedBy", "updatedAt",
                    "deleted");
        }
    };

    public List<String> updateExcludeFields = new ArrayList<String>() {
        {
            addAll(Arrays.asList("id", "uuid", "version",
                    "createdAt", "createdBy",
                    "updatedBy", "updatedAt",
                    "deleted"));
        }
    };

    @GetMapping
    public ResponseEntity<Object[]> index() {
        List<T> items = entityService.list();

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object[] mItems = ObjectUtils.modifyData(items, __getExcludeFields);

        return new ResponseEntity<>(mItems, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Object> save(@RequestBody Map<String, Object> obj) {
        T item = (T) ObjectUtils.mapToObject(domainClass, obj);
        item = entityService.save(item);
        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        obj = ObjectUtils.modifyData(item, __getExcludeFields);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}")
    ResponseEntity<Object> get(@PathVariable Long id) {
        T item = entityService.get(id, null);

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object obj = ObjectUtils.modifyData(item, __getExcludeFields);

        return ObjectUtils.isEmpty(obj) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/{id:\\d+}")
    ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        T orgItem = entityService.get(id, null);
        if (orgItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String[] __updateExcludeFields = updateExcludeFields.toArray(new String[0]);
        body = ObjectUtils.modifyData(body, __updateExcludeFields);
        body.put("id", id);

        ObjectUtils.mergeObject(orgItem, body, updateExcludeFields);
        orgItem = entityService.update(id, orgItem, null);

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);

        return new ResponseEntity<>(ObjectUtils.modifyData(orgItem, __getExcludeFields), HttpStatus.OK);
    }

    @DeleteMapping("/{id:\\d+}")
    ResponseEntity<Object> delete(@PathVariable Long id) {
        T item = entityService.delete(id);

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object obj = ObjectUtils.modifyData(item, __getExcludeFields);

        return ObjectUtils.isEmpty(obj) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping(path = { "/loadDataTable", "load-data-table" })
    ResponseEntity<?> loadDataTableG(@RequestParam Map<String, Object> params) {
        return loadDataTableP(params);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(path = { "/loadDataTable", "load-data-table" })
    ResponseEntity<?> loadDataTableP(@RequestBody Map<String, Object> params) {
        DataTableFilter dtf = (DataTableFilter) DataTableFilter.mapToObject(params);
        LoadDataTableOptions options = new LoadDataTableOptions();
        options.domainClass = domainClass;
        DataTableResponse dtr = entityService.loadDataTable(dtf, options);
        if (dtr.code == ResponseCode.SUCCESS) {
            dtr.items = dtr.items;
        }
        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        return new ResponseEntity<>(ObjectUtils.modifyData(dtr, __getExcludeFields), HttpStatus.OK);
    }
}
