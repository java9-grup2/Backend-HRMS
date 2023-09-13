package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterManagerRequestDto {

    @NotBlank(message = "username is a must")
    @Size(min = 4,max = 16,message = "Username must be between 4 to 16 character")
    private String username;
    @NotBlank(message = "password is a must")
    @Size(min = 5,max = 32,message = "Password must be between 5 to 32 character")
    private String password;

    @NotBlank(message = "email is a must")
    @Email(message = "this section must be in email format")
    private String email;

    @NotBlank(message = "TaxNo is a must")
    private String taxNo;
    @NotBlank(message = "companyName is a must")
    private String companyName;

}
