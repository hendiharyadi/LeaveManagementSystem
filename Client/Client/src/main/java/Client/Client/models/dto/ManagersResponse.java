package Client.Client.models.dto;

import Client.Client.models.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagersResponse {
    private List<Employee> managers;
}
