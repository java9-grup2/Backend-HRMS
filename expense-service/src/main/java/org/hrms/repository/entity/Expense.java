package org.hrms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EExpenseType;

import javax.persistence.*;
import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authid;
    private String companyName;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private EExpenseType expenseType;
    private String file; // ilerde dosya tutmak için
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private String currency;
    @Builder.Default
    private LocalDate requestDate=LocalDate.now();
    private LocalDate replyDate;

}
