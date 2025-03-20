package com.base.admin.application.service.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.domain.entity.personal.Personal;
import com.base.admin.infrastructure.adapter.out.repository.personal.PersonalRepository;
import com.base.common.application.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonalService extends BaseEntityService<Personal> {

    PersonalService(@Autowired PersonalRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
