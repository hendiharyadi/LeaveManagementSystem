package Client.Client.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeDto {
    private String start_overtime;
    private String end_overtime;
    private String note;
    private Integer employee_id;
    private Integer project_id;
    private Boolean status;
}
