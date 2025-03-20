package com.base.admin.domain.dto.user;

import java.util.Set;

import com.base.admin.domain.entity.personal.Personal;
import com.base.admin.domain.entity.role.Role;
import com.base.admin.domain.entity.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends User {
    Set<Role> roles;
    Personal personal;
}
