package com.base.admin.dto.exception.user;

import com.base.admin.dto.exception.CustomizeException;

public class UserNotFoundException extends CustomizeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
