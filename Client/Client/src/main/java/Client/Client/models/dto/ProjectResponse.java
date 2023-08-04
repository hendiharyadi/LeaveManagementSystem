package Client.Client.models.dto;

import Client.Client.models.entities.Employee;
import groovy.lang.Newify;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Integer id;
    private List<Employee> members;
    private String name;
    private String start_project;
    private String end_project;
}
