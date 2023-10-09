package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeRequestDto {

    @NotBlank
    private String token;
    @NotBlank(message = "Ad bilgisi bos birakilamaz")
    private String name;
    @NotBlank(message = "Soyad bilgisi bos birakilamaz")
    private String surname;
    @NotBlank(message = "mail bilgisi bos birakilamaz")
    private String personalEmail;
    @NotNull(message = "maas bilgisi bos birakialmaz")
    private Double salary;

}
