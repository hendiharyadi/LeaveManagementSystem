package mcc72.Server.repositories;

import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findPermissionByEmployee(Employee manager);

    List<Permission> findPermissionByManager(Employee manager);

    @Query("SELECT p FROM Permission as p WHERE p.employee.id= ?1 ORDER BY (CASE WHEN p.status = 'PENDING' THEN 0 WHEN p.status = 'ACCEPTED' THEN 1 ELSE 2 END)")
    List<Permission> orderPermission(Integer id);
}
