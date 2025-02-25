package com.base.admin.dto.requestmap;

import java.util.List;
import java.util.regex.Pattern;

import com.base.admin.entity.requestmap.RequestMap;

public class RequestMapDto {
    public RequestMap requestMap;
    public Pattern pattern;
    public List<String> configAttributes;
}
