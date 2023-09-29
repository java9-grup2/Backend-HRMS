package org.hrms.repository;

import org.hrms.repository.entity.ShiftsAndBreaks;
import org.hrms.repository.enums.EShiftTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IShiftsAndBreaksRepository extends JpaRepository<ShiftsAndBreaks,Long> {


    boolean existsByCompanyNameAndShiftTypes(String companyName, EShiftTypes shiftTypes);

    List<ShiftsAndBreaks> findAllByCompanyName(String companyName);
}
