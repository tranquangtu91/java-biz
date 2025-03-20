package com.base.admin.application.service.category;

import java.util.List;

import com.base.admin.domain.dto.category.GetCategoryDataDto;
import com.base.admin.domain.entity.category.CategoryData;

public interface ICategoryService {
    public List<CategoryData> getCategoryData(GetCategoryDataDto dto);
}
