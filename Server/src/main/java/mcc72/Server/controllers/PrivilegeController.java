package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.Privilege;
import mcc72.Server.services.PrivilegeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("privilege")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PrivilegeController {

    private PrivilegeService privilegeService;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap(){
        return privilegeService.getAllMap();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Privilege insert(@RequestBody Privilege privilege){
        return privilegeService.insert(privilege);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Privilege update(@RequestBody Privilege privilege){
        return privilegeService.update(privilege);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return privilegeService.deleteById(id);
    }
}
