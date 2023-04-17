package mcc72.Server.repositories;

import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("select e from Employee e where e.manager.id = ?1")
    List<Employee> getEmployeeManager (Integer id);

    @Query("select pr from Project pr where pr.manager.id =?1 order by pr.id desc")
    List<Project> orderProject(Integer id);
}
