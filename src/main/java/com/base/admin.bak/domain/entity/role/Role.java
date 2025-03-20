package com.base.admin.domain.entity.role;

import com.base.common.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_role")
@Table(indexes = {
        @Index(unique = true, columnList = "authority ASC")
})
public class Role extends BaseEntity {

    @Column(name = "authority", nullable = false, unique = true)
    String authority;

    @Column()
    Integer priority;
}
