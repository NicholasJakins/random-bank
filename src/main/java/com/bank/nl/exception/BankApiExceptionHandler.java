package com.bank.nl.exception;

import com.bank.nl.model.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.bank.nl.model.Error;

@ControllerAdvice
public class BankApiExceptionHandler {

    @ExceptionHandler(exception = BusinessException.class)
    public ResponseEntity<Error> handleGenericException(BusinessException exception) {
        return ResponseEntity.status(exception.getResponseCode().getStatusCode()).body(Error.builder()
                .code(exception.getResponseCode().getCode())
                .message(exception.getMessage()).build());
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<Error> handleGenericException(Exception exception) {
        return ResponseEntity.internalServerError()
                .body(Error.builder().code(ResponseCode.GENERIC_ERROR.getCode())
                        .message("Generic server error").build());
    }

}
