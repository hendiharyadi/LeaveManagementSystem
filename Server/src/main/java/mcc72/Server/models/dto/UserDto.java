package mcc72.Server.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String username;
    private String password;
    private Integer role_id;
    private Integer manager_id;
    private Integer stockLeave;
}
