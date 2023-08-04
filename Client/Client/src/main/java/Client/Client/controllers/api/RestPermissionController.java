package Client.Client.controllers.api;

import Client.Client.models.dto.HistoryPermissionResponse;
import Client.Client.models.dto.PermissionDto;
import Client.Client.models.dto.PermissionResponse;
import Client.Client.models.entities.Permission;
import Client.Client.services.HistoryPermissionService;
import Client.Client.services.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MANAGER')")
public class RestPermissionController {

    private PermissionService permissionService;
    private HistoryPermissionService historyPermissionService;

    @GetMapping
    public List<PermissionResponse> getAll(){
        return permissionService.getAll();
    }

    @GetMapping("/manager")
    public List<Permission> getAllByManager(){
        return permissionService.getAllByManager();
    }

    @GetMapping("/{id}")
    public PermissionResponse getById(@PathVariable Integer id){
        return permissionService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_USER','CREATE_MANAGER')")
    @PostMapping
    public PermissionResponse create(@RequestBody PermissionDto permission){
        return permissionService.create(permission);
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public PermissionResponse update (@PathVariable Integer id, @RequestBody PermissionDto permission){
        return permissionService.update(id, permission);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Permission delete(@PathVariable Integer id){
        return permissionService.delete(id);
    }

    @GetMapping("/history")
    public List<HistoryPermissionResponse> getHistory(){
        return historyPermissionService.getAll();
    }
}
