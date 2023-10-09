package org.hrms.exception;

import lombok.Getter;

@Getter
public class AdvancePaymentManagerException extends RuntimeException{

    private final ErrorType errorType;

    public AdvancePaymentManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public AdvancePaymentManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
