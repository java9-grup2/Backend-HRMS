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
    USERNAME_EXIST(4110,"Kullanici zaten mevcut",HttpStatus.BAD_REQUEST),
    COMPANY_NOT_CREATED(4111,"Sirket olusturulamadi",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4112,"Boyle bir kullanici bulunamadi",HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(4113,"Aktivasyon kodunuz uyusmadi",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4114,"Kullanici adi veya sifre hatali",HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_ACTIVE(4115,"Aktive Edilmemis hesap. Lutfen hesabinizi aktif hale getirin" ,HttpStatus.FORBIDDEN),
    INVALID_TOKEN(4116,"Gecersiz token" ,HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4117,"Token olusturulamadi" ,HttpStatus.BAD_REQUEST),
    PERSONAL_EMAIL_IS_TAKEN(4118,"Bu email sistemde kayitli" ,HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSION(4119, "Bu islemi yapmaya yetkiniz yok", HttpStatus.BAD_REQUEST),
    TAXNO_IS_BELONG_TO_ANOTHER_COMPANY(4120, "Girdiginiz vergi numarasi baska sirekete ait, lutfen dogru numarayi girdiginizden emin olunuz", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_FOUND(4120,"Boyle bir sirket bulunamadi",HttpStatus.BAD_REQUEST),
    COMPANYNAME_EXISTS(4120,"Sirket ismi zaten mevcut",HttpStatus.BAD_REQUEST),
    SELECTED_YEAR_ALREADY_EXISTS(4122,"Secili yil bilgileri zaten mevcut",HttpStatus.BAD_REQUEST),
    NO_DETAILS_FOR_SELECTED_YEAR(4123,"Secili yila ait bir bilgi bulunamamistir",HttpStatus.BAD_REQUEST),
    SHIFT_TYPE_ALREADY_EXISTS(4123,"Bu sirketin secili vardiya saatleri zaten tanimlanmistir.",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus status;
}
