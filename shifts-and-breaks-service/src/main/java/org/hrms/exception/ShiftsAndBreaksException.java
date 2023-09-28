package org.hrms.exception;

import lombok.Getter;

@Getter
public class ShiftsAndBreaksException extends RuntimeException{

    private final ErrorType errorType;

    public ShiftsAndBreaksException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public ShiftsAndBreaksException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
