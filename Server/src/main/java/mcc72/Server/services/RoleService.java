package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Role;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.RoleRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public List<Role> findAll(){
        if(roleRepository.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available.");
        }

        for(Role role : roleRepository.findAll()){
            role.getName();
        }

        return roleRepository.findAll();
    }

    public List<Map<String, Object>> getAllMap(){

        return roleRepository.findAll().stream().map(role -> {
            Map<String, Object> m = new HashMap<>();
            m.put("roleId", role.getId());
            m.put("roleName", role.getName());
            return m;
        }).collect(Collectors.toList());
    }

    public Object getRoleManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        Role role = roleRepository.findById(2).get();
        return role.getUserRole().stream().map(usr -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", usr.getEmployee().getId());
            m.put("first_name", usr.getEmployee().getFirst_name());
            m.put("last_name", usr.getEmployee().getLast_name());
            m.put("email", usr.getEmployee().getEmail());
            m.put("phone_number", usr.getEmployee().getPhone_number());
            m.put("user", usr.getEmployee().getUser());
            m.put("manager", usr.getEmployee().getManager());
            m.put("managers", usr.getEmployee().getManagers());
            m.put("employeeProject", usr.getEmployee().getProjects());
            m.put("stockLeave", usr.getEmployee().getStockLeave());
            m.put("overtimes", usr.getEmployee().getOvertimes());
            m.put("permissions", usr.getEmployee().getPermissions());
            m.put("projects", usr.getEmployee().getProjects());
            return m;
        }).collect(Collectors.toList());
    }

    public Role findById(Integer id){
        if (!roleRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        return roleRepository.findById(id).get();
    }

    public Role insert(Role r){
        if(roleRepository.existsById(r.getId())){
            throw new ResponseStatusException(HttpStatus.FOUND, "Data is existed.");
        }

        return roleRepository.save(r);
    }

    public Role update (Role r){
        if(!roleRepository.existsById(r.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        return roleRepository.save(r);
    }

    public String deleteById(Integer id){
        if(!roleRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        Role r = findById(id);
        roleRepository.deleteById(id);
        return "Delete for " + r.getName() + "success.";
    }
}
