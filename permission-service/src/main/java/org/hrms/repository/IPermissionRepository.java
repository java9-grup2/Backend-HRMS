package org.hrms.repository;
import org.hrms.repository.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

    Optional<List<Permission>> findByAuthid(Long authid);

    List<Permission> findByCompanyName(String companyName);
}
