package com.oguzhan.rig.exception;

import com.oguzhan.rig.dto.ErrorDetail;
import com.oguzhan.rig.dto.ErrorResponse;
import com.oguzhan.rig.dto.ErrorResponseWithDetail;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler  extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponseWithDetail errorResponse = new ErrorResponseWithDetail("123", "Invalid Input");
        ex.getAllErrors().stream().forEach(p->errorResponse.addError(new ErrorDetail(p.getDefaultMessage())));
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exp) {
        return new ResponseEntity<>(exp.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFound exp) {
        return new ResponseEntity<>(exp.getErrorResponse(), HttpStatus.NOT_FOUND);
    }


}
