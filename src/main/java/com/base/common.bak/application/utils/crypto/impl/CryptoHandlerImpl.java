package com.base.common.application.utils.crypto.impl;

import com.base.common.application.utils.crypto.ICryptoHandler;

public class CryptoHandlerImpl implements ICryptoHandler {
    Boolean usingBase64Encode = false;

    @Override
    public Object encrypt(Object data, CryptoOptions options) {
        throw new UnsupportedOperationException("Unimplemented method 'encrypt'");
    }

    @Override
    public Object decrypt(Object data, CryptoOptions options) {
        throw new UnsupportedOperationException("Unimplemented method 'decrypt'");
    }

}
