package com.base.admin.domain.entity.personal;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.base.admin.domain.entity.user.User;
import com.base.common.application.utils.convert.date.DateUtils;
import com.base.common.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Personal")
@Table(name = "tbl_personal")
public class Personal extends BaseEntity {
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "short_name", length = 64, nullable = true)
    String shortName;

    @Column(name = "email", nullable = true)
    String email;

    @Column(name = "gender", length = 16, nullable = true)
    String gender;

    @JsonFormat(pattern = DateUtils.ISO_DATE_TIME_FORMAT)
    @Column(name = "dob", nullable = true)
    Date dob;
}
