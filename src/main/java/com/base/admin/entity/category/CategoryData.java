package com.base.admin.entity.category;

import jakarta.persistence.*;

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
@Entity
@Table(name = "tbl_category_data", indexes = {
        @Index(columnList = "category_id"),
        @Index(columnList = "code"),
        @Index(columnList = "lang"),
})
public class CategoryData extends BaseEntity {
    // @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;

    @Column(name = "code")
    String code;

    @Column(name = "value")
    String value;

    @Column(name = "lang")
    String lang;

    @Convert(converter = JpaJsonConverter.class)
    @Column(name = "props", nullable = true)
    @Lob
    Object props = "{}";

    @Column(name = "description", length = 1024)
    String description;
}