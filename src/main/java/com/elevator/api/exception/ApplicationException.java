package com.elevator.api.exception;

public class ApplicationException extends Exception {

    private String error;

    public ApplicationException(String error) {
        super(error);
    }

    public ApplicationException(Throwable ex) {
        super(ex);
    }

}
