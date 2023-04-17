package mcc72.Server.models.dto;

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
    private Integer project_id;
    private Boolean status;
}
