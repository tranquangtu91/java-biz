package com.base.admin.repository.requestmap;

import java.util.List;

import com.base.admin.entity.requestmap.RequestMap;
import com.base.common.repository.BaseCrudRepository;

public interface RequestMapRepository extends BaseCrudRepository<RequestMap, Long> {
    List<RequestMap> findAllByActive(Boolean active);
}
