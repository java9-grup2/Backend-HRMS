package org.hrms.exception;

import lombok.Getter;

@Getter
public class FinancialPerformanceException extends RuntimeException{

    private final ErrorType errorType;

    public FinancialPerformanceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public FinancialPerformanceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
