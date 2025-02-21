package com.base.admin.entity.domain_mapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import com.base.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "DomainMapping")
@Table(name = "tbl_domain_mapping", indexes = {
        @Index(columnList = "first_domain"),
        @Index(columnList = "first_id"),
        @Index(columnList = "second_domain"),
        @Index(columnList = "second_id")
})
public class DomainMapping extends BaseEntity {
    @Column(name = "first_domain")
    String firstDomain;

    @Column(name = "first_id")
    Long firstId;

    @Column(name = "second_domain")
    String secondDomain;

    @Column(name = "second_id")
    Long secondId;
}
