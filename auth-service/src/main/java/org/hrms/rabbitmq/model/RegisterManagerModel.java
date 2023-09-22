package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterManagerModel implements Serializable {

    private Long authid;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String personalEmail;
    private String taxNo;
    private String companyName;
}
