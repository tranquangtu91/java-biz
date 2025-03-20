package com.base.admin.application.port.out.repository.requestmap;

import java.util.List;

import com.base.admin.domain.entity.requestmap.RequestMap;
import com.base.common.infrastructure.adapter.out.repository.BaseCrudRepository;

public interface RequestMapRepository extends BaseCrudRepository<RequestMap, Long> {
    List<RequestMap> findAllByActive(Boolean active);
}
