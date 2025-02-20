package com.base.admin.repository.category;

import com.base.admin.entity.category.Category;
import com.base.admin.entity.category.CategoryData;
import com.base.common.repository.BaseCrudRepository;

import java.util.List;

public interface CategoryDataRepository extends BaseCrudRepository<CategoryData, Long> {
    List<CategoryData> findByCategoryAndCodeAndLang(Category category, String code, String lang);

    List<CategoryData> findByCategoryAndLang(Category category, String lang);
}
