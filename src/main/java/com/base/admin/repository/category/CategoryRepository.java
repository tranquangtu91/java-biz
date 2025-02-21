package com.base.admin.repository.category;

import com.base.admin.entity.category.Category;
import com.base.common.repository.BaseCrudRepository;

import java.util.List;

public interface CategoryRepository extends BaseCrudRepository<Category, Long> {
    List<Category> findByCode(String code);
}
