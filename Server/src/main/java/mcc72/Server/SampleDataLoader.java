package mcc72.Server;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Privilege;
import mcc72.Server.models.entity.Role;
import mcc72.Server.repositories.PrivilegeRepository;
import mcc72.Server.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class SampleDataLoader implements CommandLineRunner {

    private final RoleRepository rr;
    private final PrivilegeRepository pr;

    @Override
    public void run(String... args) throws Exception{
        if (rr.findByName("ROLE_USER").isPresent()) {
            System.out.println("ROLE_USER data already exist.");
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(0, "ROLE_USER", null, null));
            roles.add(new Role(0, "ROLE_MANAGER", null, null));
            roles.add(new Role(0, "ROLE_ADMIN", null, null));
            rr.saveAll(roles);
        }

        if (pr.findByName("CREATE_USER").isPresent()) {
            System.out.println("CREATE_USER data already exist.");
        } else {
            List<Role> rolesUser = Arrays.asList(rr.findByName("ROLE_USER").get());
            List<Role> rolesAdmin = Arrays.asList(rr.findByName("ROLE_ADMIN").get());
            List<Role> rolesManager = Arrays.asList(rr.findByName("ROLE_MANAGER").get());
            List<Privilege> privileges = new ArrayList<>();
            privileges.add(new Privilege(0, "CREATE_USER", rolesUser));
            privileges.add(new Privilege(0, "READ_USER", rolesUser));
            privileges.add(new Privilege(0, "UPDATE_USER", rolesUser));
            privileges.add(new Privilege(0, "DELETE_USER", rolesUser));

            privileges.add(new Privilege(0, "CREATE_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "READ_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "UPDATE_MANAGER", rolesManager));
            privileges.add(new Privilege(0, "DELETE_MANAGER", rolesManager));

            privileges.add(new Privilege(0, "CREATE_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "READ_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "UPDATE_ADMIN", rolesAdmin));
            privileges.add(new Privilege(0, "DELETE_ADMIN", rolesAdmin));
            pr.saveAll(privileges);
        }
    }
}
