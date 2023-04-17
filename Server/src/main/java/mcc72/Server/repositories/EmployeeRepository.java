package mcc72.Server.repositories;

import mcc72.Server.models.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public Optional<Employee> findByEmail(String email);

    @Modifying
    @Query("update StockLeave sl set sl.stock_available = ?1 where sl.id = ?2")
    Integer setStockLeave(Integer stock_available, Integer id);
}
