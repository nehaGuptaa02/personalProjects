package com.springbootcamp.exceptions;

import com.springbootcamp.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class ECommerceException extends RuntimeException {
    private String errorCode;
    private HttpStatus statusCode;

    public ECommerceException(ErrorCode errorCode) {
        this.errorCode = errorCode.getErrorDescription();
        this.statusCode = errorCode.getStatus();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
