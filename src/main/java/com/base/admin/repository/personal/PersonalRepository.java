package com.base.admin.repository.personal;

import org.springframework.stereotype.Repository;

import com.base.admin.entity.personal.Personal;
import com.base.common.repository.BaseCrudRepository;

@Repository
public interface PersonalRepository extends BaseCrudRepository<Personal, Long> {

}
