package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveManagerMailModel implements Serializable {

    private String username;
    private String password;
    private String personalEmail;
    private String companyEmail;
    private String name;
    private String surname;
    private String companyName;
    private String taxNo;
}
