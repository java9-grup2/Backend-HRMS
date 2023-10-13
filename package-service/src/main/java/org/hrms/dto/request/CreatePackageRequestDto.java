package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.ECurrencyType;
import org.hrms.repository.enums.EPackageType;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePackageRequestDto {

    @NotNull(message = "kullanici tipi bos birakilamaz")
    private EUserType userType;

    @NotNull(message = "sirket ismi bos olamaz")
    private String companyName;

    @NotNull(message = "sirket ismi bos olamaz")
    private EPackageType packageType;

}
