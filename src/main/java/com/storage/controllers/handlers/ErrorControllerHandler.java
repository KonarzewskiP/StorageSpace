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
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(UserServiceException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() DirectorServiceException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * The method throw custom error object for WarehouseServiceException
     * <p>
     * Params: WarehouseServiceException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = WarehouseServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(WarehouseServiceException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() WarehouseServiceException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * The method throw custom error object for AddressException
     * <p>
     * Params: AddressException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = AddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(AddressException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() AddressException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }


    /**
     * The method throw custom error object for StorageRoomException
     * <p>
     * Params: StorageRoomException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = StorageRoomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(StorageRoomException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() WarehouseServiceException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * The method throw custom error object for ResourceNotFoundException
     * <p>
     * Params: ResourceNotFoundException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponseDto handleNotFoundException(ResourceNotFoundException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() ResourceNotFoundException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value());
    }

    /**
     * The method throw custom error object for QuoteDetailsException
     * <p>
     * Params: QuoteDetailsException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = QuoteDetailsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleNotFoundException(QuoteDetailsException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() QuoteDetailsException with: " + e);

        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * The method throw custom error object for PostcodeException
     * <p>
     * Params: PostcodeException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = PostcodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponseDto handleNotFoundException(PostcodeException e) throws JsonProcessingException {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() PostcodeException with: " + e);

        var error = objectMapper
                .readTree(e.getError())
                .get("error")
                .asText();

        return createCustomErrorResponse(error, e.getStatusCode().getReasonPhrase(), e.getStatusCode().value());
    }

    /**
     * The method throw custom error object for EnumParsingException
     * <p>
     * Params: EnumParsingException .
     * Returns: ResponseEntity with <code>CustomErrorResponse</code> object
     *
     * @author Pawel Konarzewski
     */
    @ExceptionHandler(value = EnumParsingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleNotFoundException(EnumParsingException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() EnumParsingException with: " + e);

        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
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
