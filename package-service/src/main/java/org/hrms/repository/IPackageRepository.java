package org.hrms.repository;

import org.hrms.repository.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPackageRepository extends JpaRepository<Package,Long> {

    @Query("select p from Package p where p.status = 'ACTIVE' and p.companyName= :x")
    Optional<Package> findActivatedCompanyPack(@Param("x") String companyName);

    @Query("select p from Package p where p.status = 'PENDING' and p.companyName= :x")
    Optional<Package> findPendingCompanyPack(@Param("x") String companyName);
}
