package com.base.common.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignedBaseEntity extends BaseEntity {
    public String sign;
    
    /**
     * Các trường dữ liệu dùng để sinh chữ ký
     */
    public String[] fieldsToSign = {};

    /**
     * Sinh chữ ký
     * @return
     */
    private String genSign() {
        return "";
    }

    /**
     * Trả về chữ ký có hợp lệ không
     * @return
     */
    public Boolean isValidSign() {
        genSign();
        return false;
    }
}
