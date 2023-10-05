package org.hrms.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.PaymentType;

import javax.persistence.*;

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
    private String paymentDate;
    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentType type; //Gida,yol vs.
}
