package com.base.admin.entity.userrole;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.base.admin.entity.role.Role;
import com.base.admin.entity.user.User;
import com.base.common.entity.BaseEntity;

@Entity
@Table(name = "tbl_user_role")
class UserRole extends BaseEntity {
    @ManyToOne
    User user;
    @ManyToOne
    Role role;
}
