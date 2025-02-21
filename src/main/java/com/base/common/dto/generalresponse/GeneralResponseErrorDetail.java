package com.base.common.dto.generalresponse;

public class GeneralResponseErrorDetail {
        // Nhóm lỗi xác thực (1xxx)
        public static final GeneralResponseTemp API_KEY_NOT_EXISTS_ERROR = new GeneralResponseTemp("1000",
                        "API Key không tồn tại", ResponseCode.ERROR);
        public static final GeneralResponseTemp API_KEY_NOT_PERMIT_ERROR = new GeneralResponseTemp("1001",
                        "API Key không có quyền", ResponseCode.ERROR);
        public static final GeneralResponseTemp API_KEY_EXPIRED_ERROR = new GeneralResponseTemp("1002",
                        "API Key không có quyền", ResponseCode.ERROR);
        public static final GeneralResponseTemp USER_LIMIT_FUNC_ERROR = new GeneralResponseTemp("1020",
                        "Chức năng đang giới hạn người sử dụng", ResponseCode.ERROR);

        // Nhóm lỗi Validate dữ liệu (2xxx)
        public static final GeneralResponseTemp PARAMS_VALIDATION_ERROR = new GeneralResponseTemp("2000",
                        "Truyền thiếu hoặc sai tham số", ResponseCode.ERROR);

        // Nhóm lỗi Nghiệp vụ (3xxx)
        public static final GeneralResponseTemp NOT_FOUND_ERROR = new GeneralResponseTemp("3001",
                        "Bản ghi không tồn tại", ResponseCode.ERROR);
        public static final GeneralResponseTemp NOT_FOUND = new GeneralResponseTemp("3001",
                        "Bản ghi không tồn tại", ResponseCode.ERROR);

        // Nhóm lỗi tích hợp hệ thống vệ tinh (4xxx)
        public static final GeneralResponseTemp SYSTEM_INTEGRATION_ERROR = new GeneralResponseTemp("4000",
                        "Lỗi tích hợp hệ thống vệ tinh", ResponseCode.ERROR);

        // Nhóm lỗi ngoại lệ (9xxx)
        public static final GeneralResponseTemp EXECEPTION_ERROR = new GeneralResponseTemp("9000",
                        "Lỗi ngoại lệ", ResponseCode.ERROR);
        public static final GeneralResponseTemp INTERNAL_SERVER_ERROR = new GeneralResponseTemp("9000",
                        "Lỗi ngoại lệ", ResponseCode.ERROR);
        public static final GeneralResponseTemp TIMEOUT_ERROR = new GeneralResponseTemp("9001",
                        "Lỗi ngoại lệ", ResponseCode.ERROR);
}
