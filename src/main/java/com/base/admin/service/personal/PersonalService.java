package com.base.admin.service.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.admin.entity.personal.Personal;
import com.base.admin.repository.personal.PersonalRepository;
import com.base.common.service.impl.BaseEntityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonalService extends BaseEntityService<Personal> {

    PersonalService(@Autowired PersonalRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
}
