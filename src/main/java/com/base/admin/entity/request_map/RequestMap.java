package com.base.admin.entity.request_map;

import jakarta.persistence.*;

import com.base.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RequestMap")
@Table(name = "tbl_request_map", indexes = {
        @Index(columnList = "active")
})
public class RequestMap extends BaseEntity {
    @Column(name = "config_attributes", nullable = false)
    String configAttributes;

    @Column(name = "http_method", nullable = true, length = 16)
    String httpMethod;

    @Column(name = "url", nullable = false)
    String url;

    @Column(name = "active", nullable = false)
    Boolean active = false;

    @Column(name = "log_body", nullable = true)
    Boolean logBody = false;
}
