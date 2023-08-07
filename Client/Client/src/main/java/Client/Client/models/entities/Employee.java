package Client.Client.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private User user;
    private List<Employee> managers;
    private Employee manager;
    private List<Project> employeeProject;
    private StockLeave stockLeave;
    private List<Overtime> overtimes;
    private List<Permission> permissions;
    private List<Project> projects;
}
