package Client.Client.models.entities;

import java.util.List;

public class Role {
    private Integer id;
    private String name;
    private List<UserEntity> userRole;
    private List<Privilege> privileges;
}
