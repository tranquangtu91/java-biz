package com.base.admin.application.port.out.repository.role;

import org.springframework.stereotype.Repository;

import com.base.admin.domain.entity.role.Role;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

@Repository
public interface RoleRepository extends BaseCrudRepository<Role, Long> {

}
