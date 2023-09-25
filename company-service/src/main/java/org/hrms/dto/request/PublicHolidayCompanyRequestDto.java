package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PublicHolidayCompanyRequestDto {
    private Long id;
    private String name;
    private String date;
    private String explanation;
}
