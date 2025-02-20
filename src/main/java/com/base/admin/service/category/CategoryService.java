package com.base.admin.service.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.dto.category.GetCategoryDataDto;
import com.base.admin.entity.category.Category;
import com.base.admin.entity.category.CategoryData;
import com.base.admin.repository.category.CategoryDataRepository;
import com.base.admin.repository.category.CategoryRepository;
import com.base.common.service.BaseEntityService;
import com.base.common.utils.convert.object.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService extends BaseEntityService<Category> {
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDataRepository categoryDataRepository;

    CategoryService(@Autowired CategoryRepository entityRepository) {
        this.entityRepository = entityRepository;
        this.categoryRepository = entityRepository;
    }

    public List<CategoryData> getCategoryData(GetCategoryDataDto dto) {
        List<Category> categories = categoryRepository.findByCode(dto.categoryCode);
        if (categories.isEmpty())
            return new ArrayList<>();

        Category category = categories.get(0);

        if (ObjectUtils.isEmpty(dto.lang))
            dto.lang = "vi_VN";

        List<CategoryData> data;
        if (ObjectUtils.isEmpty(dto.categoryDataCode)) {
            data = categoryDataRepository.findByCategoryAndLang(category, dto.lang);
        } else {
            data = categoryDataRepository.findByCategoryAndCodeAndLang(category, dto.categoryDataCode, dto.lang);
        }

        return data;
    }
}
