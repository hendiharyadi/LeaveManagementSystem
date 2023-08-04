package Client.Client.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Integer id;
    private LeaveType leave_type;
    private String start_leave;
    private String end_leave;
    private String note;
    private Status status;
    private Employee employee;
}
