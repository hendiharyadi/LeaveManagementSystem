package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.PermissionDto;
import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Permission;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.UserRepository;
import mcc72.Server.services.PermissionService;
import mcc72.Server.services.StockLeaveService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("permission")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class PermissionController {

    private PermissionService permissionService;
    private StockLeaveService stockLeaveService;
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public Object getAll(){
        return permissionService.getAll();
    }

    @GetMapping("/manager")
    public List<Permission> getPermitByManager(){
        return permissionService.getByManager();
    }

    @GetMapping("/{id}")
    public Permission getById(@PathVariable int id) {
        return permissionService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public Permission insert(@RequestBody PermissionDto permission){
        Permission permit = permissionService.create(permission);
        if(permission.getLeave_type().equals(true)){
            permissionService.sendRequestMail(permission);
        } else {
            permissionService.sendRequestPermitMail(permission);
        }
        return permit;
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Permission update(@PathVariable Integer id, @RequestBody PermissionDto permission, Employee e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        Permission permit = permissionService.update(id, permission);
        if (permission.getStatus().equals(true) && permission.getLeave_type().equals(true)){
            permissionService.sendConfirmationMail(id, permission);
            stockLeaveService.updateCuti(permission.getEmployee_id(), permission.getLeave_day());
        } else {
            permissionService.sendConfirmationMail(id, permission);
        }
        return permit;
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Permission delete (@PathVariable Integer id){
        return permissionService.delete(id);
    }
}
