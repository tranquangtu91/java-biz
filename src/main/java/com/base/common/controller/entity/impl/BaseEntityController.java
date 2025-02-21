package com.base.common.controller.entity.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.base.common.controller.entity.IEntityController;
import com.base.common.dto.datatablefilter.DataTableFilter;
import com.base.common.dto.datatableresponse.DataTableResponse;
import com.base.common.dto.generalresponse.ResponseCode;
import com.base.common.service.IEntityService;
import com.base.common.service.LoadDataTableOptions;
import com.base.common.utils.convert.object.ObjectUtils;

@SuppressWarnings("unchecked")
public class BaseEntityController<T> implements IEntityController {
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

    @Override
    public ResponseEntity<Object[]> index() {
        List<T> items = entityService.list();

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object[] mItems = ObjectUtils.modifyData(items, __getExcludeFields);

        return new ResponseEntity<>(mItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> save(Map<String, Object> obj) {
        T item = (T) ObjectUtils.mapToObject(domainClass, obj);
        item = entityService.save(item);
        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        obj = ObjectUtils.modifyData(item, __getExcludeFields);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> get(Long id) {
        T item = entityService.get(id, null);

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object obj = ObjectUtils.modifyData(item, __getExcludeFields);

        return ObjectUtils.isEmpty(obj) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Map<String, Object> body) {
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

    @Override
    public ResponseEntity<Object> delete(Long id) {
        T item = entityService.delete(id);

        String[] __getExcludeFields = getExcludeFields.toArray(new String[0]);
        Object obj = ObjectUtils.modifyData(item, __getExcludeFields);

        return ObjectUtils.isEmpty(obj) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> loadDataTableGet(Map<String, Object> params) {
        return loadDataTablePost(params);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<?> loadDataTablePost(Map<String, Object> params) {
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
