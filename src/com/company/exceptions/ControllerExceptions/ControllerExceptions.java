package com.company.exceptions.ControllerExceptions;

public class ControllerExceptions extends RuntimeException {

    public ControllerExceptions(String errorMessage) {
        super(errorMessage);
    }
}
