package org.hrms.exception;

import lombok.Getter;

@Getter
public class UpcomingPaymentManagerException extends RuntimeException{

    private final ErrorType errorType;

    public UpcomingPaymentManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public UpcomingPaymentManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
