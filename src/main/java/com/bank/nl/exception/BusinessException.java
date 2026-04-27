package com.bank.nl.exception;

import com.bank.nl.model.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BusinessException extends RuntimeException {

    @Getter
    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
