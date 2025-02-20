package com.base.admin.entity.category;

import java.util.List;
import jakarta.persistence.*;

import com.base.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name = "tbl_category", indexes = {
        @Index(columnList = "code", unique = true)
})
public class Category extends BaseEntity {
    @Column(length = 32, nullable = false)
    String code;

    @Column(length = 64, nullable = false)
    String name;

    @Column(length = 1024, nullable = true)
    String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<CategoryData> data;
}
