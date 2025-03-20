package com.base.admin.domain.dto.exception.user;

import com.base.admin.domain.dto.exception.CustomizeException;

public class WrongPasswordException extends CustomizeException {

    public WrongPasswordException(String message) {
        super(message);
    }

}
