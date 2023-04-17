package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Role;
import mcc72.Server.services.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("role")
public class RoleController {

    private RoleService rs;

    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return rs.getAllMap();
    }

    @GetMapping("/getrolemanagers")
    public Object getRoleManager(){
        return rs.getRoleManager();
    }

    @GetMapping("/managers")
    public Object getManagers(){
        return rs.getRoleManager();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Role insert(@RequestBody Role role){
        return rs.insert(role);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Role update(@RequestBody Role role){
        return rs.update(role);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return rs.deleteById(id);
    }
}
