package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequestDto {
    private Long id;
    private String companyName;
    private String taxNo;
    private Long numOfEmployees;
    private String about;
    private String phone;
    private String address;
    private String companyEmail;
    private String fax;
}
