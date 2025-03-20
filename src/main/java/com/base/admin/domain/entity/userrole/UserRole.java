package com.base.admin.domain.entity.userrole;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.base.admin.domain.entity.role.Role;
import com.base.admin.domain.entity.user.User;
import com.base.common.domain.entity.BaseEntity;

@Entity
@Table(name = "tbl_user_role")
class UserRole extends BaseEntity {
    @ManyToOne
    User user;
    @ManyToOne
    Role role;
}
