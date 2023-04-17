package mcc72.Server.repositories;

import mcc72.Server.models.entity.HistoryPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface HistoryPermissionRepository extends JpaRepository<HistoryPermission, Integer> {

    @Query("select hp from HistoryPermission hp where hp.employee.id = ?1 order by (case when hp.permission.status = 'PENDING' then 0 when hp.permission.status = 'ACCEPTED' then 1 else 2 end)")
    List<HistoryPermission> orderHistoryPermission(Integer id);
}
