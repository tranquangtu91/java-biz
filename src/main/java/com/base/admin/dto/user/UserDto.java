package com.base.admin.dto.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.base.admin.entity.personal.Personal;
import com.base.admin.entity.role.Role;
import com.base.admin.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends User {
    Set<Role> roles;
    Personal personal;
}
