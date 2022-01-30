package com.oguzhan.rig.exception;

import com.oguzhan.rig.dto.ErrorResponse;

public class BusinessException extends Exception{
    private ErrorResponse errorResponse;

    public BusinessException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
