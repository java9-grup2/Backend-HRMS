package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EUserType;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralStatusRequestDto {
    @NotNull(message = "kullanici tipi bos olamaz")
    private EUserType userType;
    @NotNull(message = "sirket ismi bos olamaz")
    private String companyName;
}
