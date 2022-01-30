package com.oguzhan.rig.exception;

import com.oguzhan.rig.dto.ErrorResponse;

public class ResourceNotFound extends Exception{
    private ErrorResponse errorResponse;

    public ResourceNotFound(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
