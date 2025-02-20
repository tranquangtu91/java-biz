package com.base.admin.service.request_map;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.request_map.RequestMap;
import com.base.admin.repository.request_map.RequestMapRepository;
import com.base.admin.utils.SpringSecurityService;
import com.base.common.service.BaseEntityService;

@Service
public class RequestMapService extends BaseEntityService<RequestMap> {

    RequestMapService(@Autowired RequestMapRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public RequestMap save(RequestMap item) {
        RequestMap requestMap = super.save(item);
        SpringSecurityService.clearCacheRequestMap();

        return requestMap;
    }

    @Override
    public RequestMap update(Long id, RequestMap item, Map<String, Object> options) {
        RequestMap requestMap = super.update(id, item, options);
        SpringSecurityService.clearCacheRequestMap();

        return requestMap;
    }

    @Override
    public RequestMap delete(Long id) {
        RequestMap requestMap = super.delete(id);
        SpringSecurityService.clearCacheRequestMap();

        return requestMap;
    }
}
