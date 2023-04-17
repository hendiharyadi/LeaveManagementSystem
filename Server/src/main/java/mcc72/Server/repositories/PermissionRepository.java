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

    @Query("select p from Permission p where p.employee.id = ?1 order by (case when p.status = 'PENDING' then 0 when p.status = 'ACCEPTED' then 1 else 2 end)")
    List<Permission> orderPermission(Integer id);
}
