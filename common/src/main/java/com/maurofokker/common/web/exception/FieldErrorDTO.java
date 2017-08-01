package com.maurofokker.common.web.exception;

/**
 * Created by mgaldamesc on 01-08-2017.
 */
public class FieldErrorDTO {

    private final String field;

    private final String message;

    public FieldErrorDTO(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    // API

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
