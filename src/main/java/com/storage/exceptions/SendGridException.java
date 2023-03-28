package com.storage.exceptions;

public class SendGridException extends EmailException {
  public SendGridException(String message) {
    super(message);
  }

  public SendGridException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
