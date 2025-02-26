package org.example.expert.domain.auth.exception;

import org.example.expert.domain.common.errorcode.ErrorCode;

public class AuthException extends RuntimeException {

    private ErrorCode errorCode;
    public AuthException (ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
