package mcc72.Server.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private String name;
    private String start_project;
    private String end_project;
    private List<Integer> employees;

}
