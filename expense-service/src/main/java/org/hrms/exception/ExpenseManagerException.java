package org.hrms.exception;

import lombok.Getter;

@Getter
public class ExpenseManagerException extends RuntimeException{

    private final ErrorType errorType;

    public ExpenseManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public ExpenseManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
