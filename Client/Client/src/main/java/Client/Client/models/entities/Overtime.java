package Client.Client.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Overtime {
    private Integer id;
    private String note;
    private String start_overtime;
    private String end_overtime;
    private Status status;
    private List<HistoryOvertime> historyOvertime;
    private Employee employee;
    private Employee manager;
    private Project project;
}
