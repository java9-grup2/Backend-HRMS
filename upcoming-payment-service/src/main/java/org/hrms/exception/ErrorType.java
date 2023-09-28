package org.hrms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR_SERVER(5100,"Sunucu Hatasi",INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100, "Parametre hatasi", HttpStatus.BAD_REQUEST),
    USERNAME_EXIST(4110, "Kullanici zaten mevcut", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND(4600, "Gosterilecek odeme bulunmamaktadır", HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus status;
}
