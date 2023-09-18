package org.hrms.repository;

import org.hrms.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {

    boolean existsByTaxNo(String taxNo);

    boolean existsByCompanyName(String companyName);
}
