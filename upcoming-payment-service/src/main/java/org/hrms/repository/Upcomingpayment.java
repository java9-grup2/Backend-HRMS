package org.hrms.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.EPaymentType;
import org.hrms.repository.enums.EPaymetStatus;
import org.hrms.repository.enums.EPaymentType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Upcomingpayment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String companyName;
    private String paymentName;
    private String paymentAmount;
    private LocalDate paymentDate;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EPaymetStatus status=EPaymetStatus.PAYABLE;

}
