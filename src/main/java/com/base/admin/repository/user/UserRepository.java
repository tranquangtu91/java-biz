package com.base.admin.repository.user;

import com.base.admin.entity.user.User;
import com.base.common.repository.BaseCrudRepository;

import java.util.List;

public interface UserRepository extends BaseCrudRepository<User, Long> {
    List<User> findByUsername(String username);
}
