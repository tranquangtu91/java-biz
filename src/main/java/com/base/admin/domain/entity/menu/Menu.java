package com.base.admin.domain.entity.menu;

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
@Entity(name = "Menu")
@Table(name = "tbl_menu", indexes = {
        @Index(columnList = "code", unique = true),
        @Index(columnList = "hide")
})
public class Menu extends BaseEntity {
    @Column(length = 32, nullable = false)
    String code;

    @Column(length = 64, nullable = false)
    String name;

    @Column(nullable = true)
    String href;

    @Column(length = 32, nullable = true)
    String icon;

    @Column(nullable = true)
    Boolean hide;

    @Column(length = 1024, nullable = true)
    String description;
}
