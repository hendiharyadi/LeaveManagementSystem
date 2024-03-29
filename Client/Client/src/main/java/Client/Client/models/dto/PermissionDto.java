package Client.Client.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Boolean leave_type;
    private String start_leave;
    private String end_leave;
    private String note;
    private Boolean status;
    private Integer employee_id;
    private Integer leave_day;
}
