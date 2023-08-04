package Client.Client.models.dto;

import Client.Client.models.entities.Employee;
import Client.Client.models.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPermissionResponse {
    private Integer id;
    private Permission permission;
    private String date_history;
    private String status;
    private Employee employee;
}
