package com.base.common.utils.convert;

import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvertOption {
    public Pattern pattern;
    public IConvertHandler<?> handler;
}
