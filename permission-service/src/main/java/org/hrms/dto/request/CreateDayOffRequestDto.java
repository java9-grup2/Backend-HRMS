package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EUserType;
import org.hrms.repository.enums.TypeOfPermit;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDayOffRequestDto {

    String token;
    //  bu metodu kullanarak izin olusturan kisinin kontrolu yapilmasi icin izin alanin tipi degil
    private EUserType userType;
    // sadece kendi sirketindeki calisanlara izin verebilmesi icin
    private String companyName;
    private TypeOfPermit typeOfPermit;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate;

}
