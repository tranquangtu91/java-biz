package com.base.admin.infrastructure.adapter.in.rest.category;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.admin.application.service.category.CategoryDataService;
import com.base.admin.domain.entity.category.CategoryData;
import com.base.common.infrastructure.adapter.in.rest.entity.impl.BaseEntityController;

@RestController()
@RequestMapping(path = "/api/v1/category-data")
public class CategoryDataController extends BaseEntityController<CategoryData> {

    CategoryDataController(@Autowired CategoryDataService entityService) {
        this.entityService = entityService;
        this.domainClass = CategoryData.class;
        getExcludeFields = Arrays.asList("updatedBy", "updatedAt", "deleted");
        updateExcludeFields = Arrays.asList("id", "uuid", "version",
                "createdAt", "createdBy",
                "updatedBy", "updatedAt",
                "deleted",
                "code");
    }
}
