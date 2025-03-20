package com.base.admin.application.service.requestmap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.application.utils.SpringSecurityService;
import com.base.admin.domain.entity.requestmap.RequestMap;
import com.base.admin.infrastructure.adapter.out.repository.requestmap.RequestMapRepository;
import com.base.common.application.service.impl.BaseEntityService;

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
