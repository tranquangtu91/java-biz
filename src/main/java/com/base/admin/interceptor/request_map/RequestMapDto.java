package com.base.admin.interceptor.request_map;

import java.util.List;
import java.util.regex.Pattern;

import com.base.admin.entity.request_map.RequestMap;

public class RequestMapDto {
    public RequestMap requestMap;
    public Pattern pattern;
    public List<String> configAttributes;
}
