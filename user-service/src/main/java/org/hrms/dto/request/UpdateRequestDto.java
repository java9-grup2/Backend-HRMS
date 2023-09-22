package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto {

    private String token;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String personalEmail;
    private String taxNo;
    private String companyName;
}
