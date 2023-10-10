package org.hrms.exception;

import lombok.Getter;

@Getter
public class PackageManagerException extends RuntimeException{

    private final ErrorType errorType;

    public PackageManagerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }


    public PackageManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
