package com.base.admin.entity.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.base.admin.entity.personal.Personal;
import com.base.admin.entity.role.Role;
import com.base.common.entity.BaseEntity;
import com.base.common.utils.convert.jpa.JpaJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_user")
@Table(indexes = {
        @Index(unique = true, columnList = "username ASC")
})
public class User extends BaseEntity {
    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "account_expired", nullable = false)
    Boolean accountExpired = false;

    @Column(name = "account_locked", nullable = false)
    Boolean accountLocked = false;

    @Column(name = "enabled", nullable = false)
    Boolean enabled = true;

    @Column(name = "password_expired", nullable = false)
    Boolean passwordExpired = false;

    @Convert(converter = JpaJsonConverter.class)
    @Column(name = "props", nullable = true)
    @Lob
    Map<String, ?> props = new HashMap<>();

    @OneToOne(mappedBy = "user")
    Personal personal;

    @ManyToMany
    @JoinTable(name = "tbl_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;

    @Override
    public void onPrePersit() {
        super.onPrePersit();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        password = "{bcrypt}" + bCryptPasswordEncoder.encode(password);
    }

    @Override
    public void onPreUpdate() {
        super.onPreUpdate();
        
        if (!this.password.startsWith("{bcrypt}")) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            password = "{bcrypt}" + bCryptPasswordEncoder.encode(password);
        }
    }
}
