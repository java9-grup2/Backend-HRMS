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
    PERSONEL_IS_NOT_EXIST(4110,"Bu personel bulunamadı.",HttpStatus.BAD_REQUEST),
    GUEST_PERMISSION(4110,"ziyaretçiler izin talebinde bulunamaz",HttpStatus.BAD_REQUEST),

    PERMISSION_IS_NOT_EXIST(1100,"Böyle bir talep bulunamadı.",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus status;
}
