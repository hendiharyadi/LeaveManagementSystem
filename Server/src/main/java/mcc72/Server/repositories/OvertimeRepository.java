package mcc72.Server.repositories;

import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OvertimeRepository extends JpaRepository<Overtime, Integer> {

    List<Overtime> findOvertimeByManager(Employee manager);

    @Query("select o from Overtime o where o.employee.id = ?1 order by (case when o.status = 'PENDING' then 0 when o.status = 'ACCEPTED' then 1 else 2 end)")
    List<Overtime> orderOvertime(Integer id);
}
