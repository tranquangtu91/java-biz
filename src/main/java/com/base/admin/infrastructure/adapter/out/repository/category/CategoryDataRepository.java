package com.base.admin.infrastructure.adapter.out.repository.category;

import com.base.admin.domain.entity.category.Category;
import com.base.admin.domain.entity.category.CategoryData;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

import java.util.List;

public interface CategoryDataRepository extends BaseCrudRepository<CategoryData, Long> {
    List<CategoryData> findByCategoryAndCodeAndLang(Category category, String code, String lang);

    List<CategoryData> findByCategoryAndLang(Category category, String lang);
}
