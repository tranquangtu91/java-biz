package com.base.admin.domain.dto.exception.user;

import com.base.admin.domain.dto.exception.CustomizeException;

public class UserNotFoundException extends CustomizeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
