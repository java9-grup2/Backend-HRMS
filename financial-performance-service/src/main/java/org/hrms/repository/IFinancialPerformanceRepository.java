package org.hrms.repository;

import org.hrms.repository.entity.FinancialPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFinancialPerformanceRepository extends JpaRepository<FinancialPerformance,Long> {

    List<FinancialPerformance> findByCompanyName(String companyName);

    boolean existsByCompanyName(String companyName);
}
