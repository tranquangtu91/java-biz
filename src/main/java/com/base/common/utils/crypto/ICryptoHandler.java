package com.base.common.utils.crypto;

public interface ICryptoHandler {
    public Object encrypt(Object data, CryptoOptions options);

    public Object decrypt(Object data, CryptoOptions options);

    public class CryptoOptions {
        /**
         * Thuật toán mã hóa
         */
        CryptoAlgorithm algorithm = CryptoAlgorithm.MD5;

        /**
         * Phương thức chuyển đổi giá trị sang Object mong muốn
         */
        IPlanToObject planToObject;
    }

    public interface IPlanToObject {
        public Object callback(Object value);
    }

    public enum CryptoAlgorithm {
        AES("aes"), MD5("md5"), SHA1("sha1"), SHA1_WITH_RSA("sha1_with_rsa");

        public final String value;

        private CryptoAlgorithm(String value) {
            this.value = value;
        }
    }
}
