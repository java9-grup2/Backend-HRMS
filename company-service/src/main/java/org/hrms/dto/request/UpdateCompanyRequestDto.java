package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequestDto {
    private Long id;
    @NotBlank(message = "sirket ismi bos birakilamaz")
    private String companyName;
    @NotBlank(message = "vergi no bos birakilamaz")
    @Size(min = 10,max=15,message = "vergi numarasi en az 10 en fazla 15 karakterden olusmalidir")
    private String taxNo;
    private Long numOfEmployees;
    private String about;
    private String phone;
    private String address;
    private String companyEmail;
    private String fax;
}
