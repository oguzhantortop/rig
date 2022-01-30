package com.oguzhan.rig.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseWithDetail extends ErrorResponse{

    private List<ErrorDetail> errorList = new ArrayList<>();

    public ErrorResponseWithDetail(String errorCode, String message) {
        super(errorCode, message);
    }

    public void addError(ErrorDetail errDetail) {
        this.errorList.add(errDetail);
    }

    public List<ErrorDetail> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorDetail> errorList) {
        this.errorList = errorList;
    }

}
