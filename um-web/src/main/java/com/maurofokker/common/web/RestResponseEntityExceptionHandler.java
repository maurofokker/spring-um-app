package com.maurofokker.common.web;

import com.maurofokker.common.web.exception.ApiError;
import com.maurofokker.common.web.exception.MyBadRequestException;
import com.maurofokker.common.web.exception.MyConflictException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Created by mgaldamesc on 28-07-2017.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Extending the response entity exception handler for full control of status codes,
     * and over the bodies of the responses sended to the client.
     * This provide good, clean responses, helpful responses, and not an empty body
     */

    private Logger log = LoggerFactory.getLogger(getClass());

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // Clients error handle 4XX

    /**
     *  simple way of returning the response entity back to the client along with a lot of useful
     *  or potentially useful information
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers
            , final HttpStatus status, final WebRequest request) {

        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * handle response to dealing with this method argument not valid exception
     * i.e. a not null validation in a property
     * should have @Valid in controller method
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers
            , final HttpStatus status, final WebRequest request) {

        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * to handle validation exceptions i.e. data integrity in db (null not enable)
     */
    @ExceptionHandler(value = { DataIntegrityViolationException.class, MyBadRequestException.class, ConstraintViolationException.class})
    public final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * message method uses the HTTP status and the exception to create this API error data transfer object
     */
    private ApiError message(final HttpStatus httpStatus, final Exception ex) {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(ex);

        return new ApiError(httpStatus.value(), message, devMessage);
    }
}
