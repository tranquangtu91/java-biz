package com.base.admin.repository.role;

import org.springframework.stereotype.Repository;

import com.base.admin.entity.role.Role;
import com.base.common.repository.BaseCrudRepository;

@Repository
public interface RoleRepository extends BaseCrudRepository<Role, Long> {

}
