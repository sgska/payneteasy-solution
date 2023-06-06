package org.payneteasy.solution.context.exception;

public class MissingPropertyException extends RuntimeException {

    private static final String ERROR_MESSAGE_PATTERN = "Missing required property: %s";

    public MissingPropertyException(String property) {
        super(String.format(ERROR_MESSAGE_PATTERN, property));
    }
}
