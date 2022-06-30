
package com.zgb.water.common.exception;

import com.zgb.water.common.base.ResponseCode;

/**
 * 业务级异常
 */
@SuppressWarnings("unused")
public class BusinessException extends RuntimeException {
    static final long serialVersionUID = 1L;

    private String errorCode = null;

    public BusinessException(String message) {
        super(message);
        this.errorCode = ResponseCode.BUSSINESS_EXCEPTION.code();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}