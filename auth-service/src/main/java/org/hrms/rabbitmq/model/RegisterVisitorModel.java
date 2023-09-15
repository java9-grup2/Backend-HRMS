package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVisitorModel implements Serializable {

    private String username;
    private String password;
    private String personalEmail;
}
