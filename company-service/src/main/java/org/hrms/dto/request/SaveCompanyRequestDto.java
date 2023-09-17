package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCompanyRequestDto {

    private Long managerId;
    private String companyName;
    private String taxNo;

    private Long numOfEmployees;
}
