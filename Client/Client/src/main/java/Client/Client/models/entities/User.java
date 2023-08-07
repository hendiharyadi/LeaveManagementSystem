package Client.Client.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean isActive;
    private String verificationCode;
    private Integer failedAttempt;
    private Employee employee;
    private List<Role> userRole;
}
