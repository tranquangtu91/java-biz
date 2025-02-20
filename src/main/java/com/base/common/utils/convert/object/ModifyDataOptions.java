package com.base.common.utils.convert.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDataOptions {
    public Boolean depthModify = false;
    public Boolean removeNullObject = false;
}
