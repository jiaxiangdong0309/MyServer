package com.example.server.exception;

import com.example.server.common.ResultCode;
import lombok.Getter;

/**
 * 自定义API异常
 */
@Getter
public class ApiException extends RuntimeException {
    private final Integer code;
    private final String message;

    public ApiException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(String message) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
        this.message = message;
    }
}