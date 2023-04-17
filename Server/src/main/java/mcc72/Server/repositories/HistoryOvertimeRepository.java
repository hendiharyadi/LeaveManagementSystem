package mcc72.Server.repositories;

import mcc72.Server.models.entity.HistoryOvertime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface HistoryOvertimeRepository extends JpaRepository<HistoryOvertime, Integer> {

    public Optional<HistoryOvertime> findById(Integer id);

    @Query("select ho from HistoryOvertime ho where ho.employee.id = ?1 order by (case when ho.overtime.status = 'PENDING' then 0 when ho.overtime.status = 'ACCEPTED' then 1 else 2 end)")
    List<HistoryOvertime>orderHistoryPermission(Integer id);
}
