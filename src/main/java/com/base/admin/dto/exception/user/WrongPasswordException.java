package com.base.admin.dto.exception.user;

import com.base.admin.dto.exception.CustomizeException;

public class WrongPasswordException extends CustomizeException {

    public WrongPasswordException(String message) {
        super(message);
    }

}
