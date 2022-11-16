package com.storage.exceptions;

public class EmailException extends BadRequestException{
    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
