package org.hrms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.EShiftTypes;

import javax.persistence.*;
import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShiftsAndBreaks extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private EShiftTypes shiftTypes;

    private LocalDate shiftStartsAt;
    private LocalDate shiftEndsAt;

    private LocalDate breakStartsAt;
    private LocalDate breakEndsAt;
}
