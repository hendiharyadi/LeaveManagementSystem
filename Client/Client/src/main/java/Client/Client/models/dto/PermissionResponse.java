package Client.Client.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class PermissionResponse {
    private Integer id;
    private String leave_type;
    private String start_leave;
    private String end_leave;
    private String note;
    private String status;
}
