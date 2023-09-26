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
    USER_NOT_CREATED(4111,"Kullanici olusturulamadi",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4112,"Boyle bir kullanici bulunamadi",HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(4113,"Aktivasyon kodunuz uyusmadi",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4114,"Kullanici adi veya sifre hatali",HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_ACTIVE(4115,"Aktive Edilmemis hesap. Lutfen hesabinizi aktif hale getirin" ,HttpStatus.FORBIDDEN),
    INVALID_TOKEN(4116,"Gecersiz token" ,HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4117,"Token olusturulamadi" ,HttpStatus.BAD_REQUEST),
    PERSONAL_EMAIL_IS_TAKEN(4118,"Bu sahsi email sistemde kayitli, lutfen baska bir mail deneyiniz" ,HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSION(4119, "Bu islemi yapmaya yetkiniz yok", HttpStatus.BAD_REQUEST),
    USERNAME_OR_MAIL_EXIST(4120,"Kullanici adi veya mail zaten mevcut",HttpStatus.BAD_REQUEST),
    COMPANY_EMAIL_IS_TAKEN(4121,"Bu sirket maili baskasina tanimlanmistir.",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1111,"Kayıt edilmemiş.",HttpStatus.BAD_REQUEST),
    USER_ALREADY_APPROVED(12324,"Kullanici zaten onaylanmis",HttpStatus.BAD_REQUEST),
    USER_TYPE_MISMATCH(12324,"Kullanicinin tipi bu istege uygun degildir.",HttpStatus.BAD_REQUEST),
    COULD_NOT_DELETE_ALL_USERS(12324,"Kullanicilarin hepsi silinemedi.",HttpStatus.BAD_REQUEST),
    COULD_NOT_UPDATE_ALL_USERS(12324,"Kullanicilarin hepsi guncellenmedi.",HttpStatus.BAD_REQUEST),
    NO_DATA_FOUND(12324,"Aradiginiz kriterlerde kullanici/kullanicilar bulunamadi.",HttpStatus.BAD_REQUEST),
    TOKEN_USER_OR_COMPANY_NAME_NOT_VALID(12325,"Alinan token, kullanici bilgileri mevcut degil, veya kendi calistiginiz sirket disinda bir sirket girmeye calisiyorusunuz.",HttpStatus.BAD_REQUEST),
    NO_PENDING_COMMENT(12325,"Onay bekleyen yorum yok.",HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(12325,"Boyle bir yorum bulunamadi.",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus status;
}
