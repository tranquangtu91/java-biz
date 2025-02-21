package com.base.admin.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.category.CategoryData;
import com.base.admin.repository.category.CategoryDataRepository;
import com.base.common.service.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryDataService extends BaseEntityService<CategoryData> {

    CategoryDataService(@Autowired CategoryDataRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
