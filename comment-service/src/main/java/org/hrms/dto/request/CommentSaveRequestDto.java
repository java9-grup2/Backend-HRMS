package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveRequestDto {



    private String token;
    private String comment;
    private String companyName;

    private EUserType userType;

}
