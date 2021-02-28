package com.storage.controller.handlers;

import com.storage.exception.DirectorServiceException;
import com.storage.exception.ResourceNotFoundException;
import com.storage.exception.WarehouseServiceException;
import com.storage.model.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorControllerHandler {

    @ExceptionHandler(value = DirectorServiceException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationException(DirectorServiceException e) {
        log.info("Enter ErrorControllerHandler -> handleValidationException() DirectorServiceException with: " + e);
        return new ResponseEntity<>(
                createCustomErrorResponse(e, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WarehouseServiceException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationException(WarehouseServiceException e) {
        log.info("Enter ErrorControllerHandler -> handleValidationException() WarehouseServiceException with: " + e);
        return new ResponseEntity<>(
                createCustomErrorResponse(e, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(ResourceNotFoundException e) {
        log.info("Enter ErrorControllerHandler -> handleNotFoundException() ResourceNotFoundException with: " + e);

        return new ResponseEntity<>(
                createCustomErrorResponse(e, HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }


    private CustomErrorResponse createCustomErrorResponse(Exception e, String errorCode, Integer status) {
        return CustomErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(e.getMessage())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
