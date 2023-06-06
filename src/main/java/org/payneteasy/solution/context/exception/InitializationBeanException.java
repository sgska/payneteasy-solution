package org.payneteasy.solution.context.exception;

public class InitializationBeanException extends RuntimeException {

    public InitializationBeanException(String message) {
        super(message);
    }

    public InitializationBeanException(String message, Throwable cause) {
        super(message, cause);
    }

}
