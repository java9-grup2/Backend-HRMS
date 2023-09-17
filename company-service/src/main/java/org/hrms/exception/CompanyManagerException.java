package org.hrms.exception;

import lombok.Getter;

@Getter
public class CompanyManagerException extends RuntimeException{

    private final ErrorType errorType;

    public CompanyManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public CompanyManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
