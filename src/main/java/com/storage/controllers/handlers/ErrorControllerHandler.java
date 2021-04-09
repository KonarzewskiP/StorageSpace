package com.storage.controllers.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.exceptions.*;
import com.storage.models.dto.CustomErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorControllerHandler {

    private final ObjectMapper objectMapper;

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = UserServiceException.class)
    public ResponseEntity<CustomErrorResponseDto> handleValidationException(UserServiceException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() DirectorServiceException with: " + e);
        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = WarehouseServiceException.class)
    public ResponseEntity<CustomErrorResponseDto> handleValidationException(WarehouseServiceException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() WarehouseServiceException with: " + e);
        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = StorageRoomException.class)
    public ResponseEntity<CustomErrorResponseDto> handleValidationException(StorageRoomException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() WarehouseServiceException with: " + e);
        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponseDto> handleNotFoundException(ResourceNotFoundException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() ResourceNotFoundException with: " + e);

        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = QuoteDetailsException.class)
    public ResponseEntity<CustomErrorResponseDto> handleNotFoundException(QuoteDetailsException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() QuoteDetailsException with: " + e);

        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = PostcodeException.class)
    public ResponseEntity<CustomErrorResponseDto> handleNotFoundException(PostcodeException e) throws JsonProcessingException {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() PostcodeException with: " + e);

        var error = objectMapper
                .readTree(e.getError())
                .get("error")
                .asText();

        return new ResponseEntity<>(
                createCustomErrorResponse(error, e.getStatusCode().getReasonPhrase(), e.getStatusCode().value()),
                e.getStatusCode());
    }

    /**
     * The method throw custom error object for UserServiceException
     * <p>
     * Params: UserServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = EnumParsingException.class)
    public ResponseEntity<CustomErrorResponseDto> handleNotFoundException(EnumParsingException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() EnumParsingException with: " + e);

        return new ResponseEntity<>(
                createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * The method that creates custom CustomErrorResponse
     * <p>
     * Params: Exception - specific exception.
     * Params: errorCode - http error message.
     * Params: status - http code status.
     * Returns: <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    private CustomErrorResponseDto createCustomErrorResponse(String msg, String errorCode, Integer status) {
        return CustomErrorResponseDto.builder()
                .errorCode(errorCode)
                .errorMessage(msg)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
