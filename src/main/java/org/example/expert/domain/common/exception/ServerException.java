package org.example.expert.domain.common.exception;

import org.example.expert.domain.common.errorcode.ErrorCode;

public class ServerException extends RuntimeException {

    private ErrorCode errorCode;
    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
