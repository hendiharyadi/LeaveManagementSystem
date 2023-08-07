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
    private RoleService roleService;

    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return roleService.getAllMap();
    }

    @GetMapping("/getrolemanagers")
    public Object getRoleManager(){
        return roleService.getRoleManager();
    }

    @GetMapping("/managers")
    public Object getManagers(){
        return roleService.getRoleManager();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Role insert(@RequestBody Role role){
        return roleService.insert(role);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Role update(@RequestBody Role role){
        return roleService.update(role);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return roleService.deleteById(id);
    }
}
