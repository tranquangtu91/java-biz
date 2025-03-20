package com.base.admin.application.service.category.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.application.port.out.repository.category.CategoryDataRepository;
import com.base.admin.domain.entity.category.CategoryData;
import com.base.common.application.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryDataService extends BaseEntityService<CategoryData> {

    CategoryDataService(@Autowired CategoryDataRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
