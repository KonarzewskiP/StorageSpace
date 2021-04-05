package com.storage.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostcodeException extends RuntimeException {
    private HttpStatus statusCode;
    private String error;

    public PostcodeException(HttpStatus statusCode, String error) {
        super(error);
        this.statusCode = statusCode;
        this.error = error;
    }
}
