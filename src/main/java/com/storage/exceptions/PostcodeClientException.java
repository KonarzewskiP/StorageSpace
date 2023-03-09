package com.storage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostcodeClientException extends RuntimeException {
    private String error;

    public PostcodeClientException(String error) {
        super(error);
        this.error = error;
    }
}
