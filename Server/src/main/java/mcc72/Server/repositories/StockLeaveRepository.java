package mcc72.Server.repositories;

import mcc72.Server.models.entity.StockLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLeaveRepository extends JpaRepository<StockLeave, Integer> {
}
