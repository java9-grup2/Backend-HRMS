package org.hrms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long managerId;
    private String companyName;
    private String taxNo;

    private Long numOfEmployees;

    //ayni isimde sirket olmasi durumunda sirketin ne uzerine kurulduguna bakarak ayirt edebilmek icin yapilmistir
    private String about;
}
