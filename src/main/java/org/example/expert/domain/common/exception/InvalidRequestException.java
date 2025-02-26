package org.example.expert.domain.common.exception;

import org.example.expert.domain.common.errorcode.ErrorCode;

public class InvalidRequestException extends RuntimeException {
    private ErrorCode errorCode;
    public InvalidRequestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
