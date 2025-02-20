package com.base.admin.repository.request_map;

import java.util.List;

import com.base.admin.entity.request_map.RequestMap;
import com.base.common.repository.BaseCrudRepository;

public interface RequestMapRepository extends BaseCrudRepository<RequestMap, Long> {
    List<RequestMap> findAllByActive(Boolean active);
}
