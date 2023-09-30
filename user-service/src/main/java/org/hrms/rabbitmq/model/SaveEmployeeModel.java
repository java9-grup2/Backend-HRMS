package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveEmployeeModel implements Serializable {

    private Long authid;
    private String name;
    private String surname;
    private String username;
    private String personalEmail;
    private String companyEmail;
    private String password;
    private String companyName;
    private EUserType userType;
    private EStatus status;
    private String phoneNumber;
    private Double salary;
}
