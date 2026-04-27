package com.bank.nl.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    GENERIC_ERROR(100, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS(101, HttpStatus.BAD_REQUEST),
    WRONG_CREDENTIALS(102, HttpStatus.BAD_REQUEST)
    ;

    private final int code;
    private final HttpStatus statusCode;

    ResponseCode(int code, HttpStatus statusCode) {
        this.code = code;
        this.statusCode = statusCode;
    }
}
