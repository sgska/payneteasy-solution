package org.payneteasy.solution.context.exception;

public class MissingImplementationException extends RuntimeException {

    private static final String ERROR_MESSAGE_PATTERN = "Missing implementation interface: %s";

    public MissingImplementationException(Class<?> interfaceClass) {
        super(String.format(ERROR_MESSAGE_PATTERN, interfaceClass.getName()));
    }

}
