package com.base.admin.application.port.out.repository.user;

import com.base.admin.domain.entity.user.User;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

import java.util.List;

public interface UserRepository extends BaseCrudRepository<User, Long> {
    List<User> findByUsername(String username);
}
