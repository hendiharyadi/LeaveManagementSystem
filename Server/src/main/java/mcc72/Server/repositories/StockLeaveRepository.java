package mcc72.Server.repositories;

import mcc72.Server.models.entity.StockLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLeaveRepository extends JpaRepository<StockLeave, Integer> {
}
