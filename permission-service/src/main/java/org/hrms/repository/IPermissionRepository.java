package org.hrms.repository;
import org.hrms.repository.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findOptionalByWorkerid(Long workerid);
    List<Permission> findOptionalByManagerid(Long managerid);

}
