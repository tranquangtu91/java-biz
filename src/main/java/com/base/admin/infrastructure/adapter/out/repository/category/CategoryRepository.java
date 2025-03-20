package com.base.admin.infrastructure.adapter.out.repository.category;

import com.base.admin.domain.entity.category.Category;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

import java.util.List;

public interface CategoryRepository extends BaseCrudRepository<Category, Long> {
    List<Category> findByCode(String code);
}
