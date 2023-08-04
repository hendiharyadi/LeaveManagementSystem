package Client.Client.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Integer id;
    private Employee manager;
    private String name;
    private String start_project;
    private String end_project;
    private Boolean status;
    private List<Employee> employeeProject;
    private List<Overtime> overtimes;
}
