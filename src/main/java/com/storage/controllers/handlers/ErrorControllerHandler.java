package com.storage.controllers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.exceptions.*;
import com.storage.models.dto.CustomErrorResponseDto;
import com.storage.security.exceptions.AppSecurityException;
import com.storage.security.tokens.exception.AppTokensServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(value = ObjectValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(ObjectValidationException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() ObjectValidationException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = AppSecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomErrorResponseDto handleValidationException(AppSecurityException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() AppSecurityException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(value = AppTokensServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(AppTokensServiceException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() AppTokensServiceException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleValidationException(BadRequestException e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() BadRequestException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponseDto handleUnknownException(Exception e) {
        log.error("Enter ErrorControllerHandler -> handleValidationException() Exception with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value());
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
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorResponseDto handleNotFoundException(NotFoundException e) {
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
    @ExceptionHandler(value = PostcodeClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorResponseDto handleNotFoundException(PostcodeClientException e) {
        log.error("Enter ErrorControllerHandler -> handleNotFoundException() PostcodeException with: " + e);
        return createCustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());
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
