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
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String personalEmail;
    @NotNull
    private Double salary;

}
