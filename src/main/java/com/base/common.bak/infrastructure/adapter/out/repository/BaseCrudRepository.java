package com.base.common.infrastructure.adapter.out.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseCrudRepository<T1, T2> extends CrudRepository<T1, T2> {
}
