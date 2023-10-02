package org.hrms.repository;
import org.hrms.repository.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

    Optional<List<Permission>> findByAuthid(Long authid);

    @Query("SELECT p FROM Permission p WHERE p.approvalStatus = 'PENDING' AND p.companyName = :companyName")
    List<Permission> findByCompanyName(@Param("companyName") String companyName);
}
