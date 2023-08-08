package Client.Client.models.dto;

import Client.Client.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private User user;

}
