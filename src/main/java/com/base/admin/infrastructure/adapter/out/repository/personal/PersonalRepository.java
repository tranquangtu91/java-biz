package com.base.admin.infrastructure.adapter.out.repository.personal;

import org.springframework.stereotype.Repository;

import com.base.admin.domain.entity.personal.Personal;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

@Repository
public interface PersonalRepository extends BaseCrudRepository<Personal, Long> {

}
