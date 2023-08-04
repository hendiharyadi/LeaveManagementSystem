package Client.Client.models.dto;

import Client.Client.models.entities.Employee;
import Client.Client.models.entities.Overtime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryOvertimeResponse {
    private Integer id;
    private Overtime overtime;
    private String date_history;
    private String status;
    private Employee employee;

}
