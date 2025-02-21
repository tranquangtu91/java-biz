package com.base.common.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import com.base.common.utils.convert.date.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Version
    @Column(name = "version")
    Integer version = 0;

    @Column(name = "uuid", nullable = false, length = 64)
    String uuid = UUID.randomUUID().toString();

    @Column(name = "created_by", nullable = false, length = 32)
    String createdBy = "unknown";

    @JsonFormat(pattern = DateUtils.ISO_DATE_TIME_FORMAT)
    @Column(name = "created_at", nullable = false)
    Date createdAt = new Date();

    @Column(name = "updated_by", nullable = true, length = 32)
    String updatedBy;

    @JsonFormat(pattern = DateUtils.ISO_DATE_TIME_FORMAT)
    @Column(name = "updated_at", nullable = true)
    Date updatedAt = new Date();

    @Column(name = "deleted", nullable = true, length = 1)
    Boolean deleted = false;

    @Column(name = "deleted_by", nullable = true, length = 32)
    String deletedBy;

    @JsonFormat(pattern = DateUtils.ISO_DATE_TIME_FORMAT)
    @Column(name = "deleted_at", nullable = true)
    Date deletedAt;

    @PrePersist
    public void onPrePersit() {
    }

    @PostPersist
    public void onPostPersit() {
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = new Date();
    }

    @PostUpdate
    public void onPostUpdate() {
    }
}
