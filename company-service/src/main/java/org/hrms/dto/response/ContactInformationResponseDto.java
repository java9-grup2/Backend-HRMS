package org.hrms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformationResponseDto {

    private String phone;
    private String address;
    private String companyEmail;
    private String fax;
}
