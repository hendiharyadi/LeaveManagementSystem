package Client.Client.controllers.api;

import Client.Client.models.dto.HistoryPermissionResponse;
import Client.Client.models.entities.HistoryPermission;
import Client.Client.services.HistoryOvertimeService;
import Client.Client.services.HistoryPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history/permission")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
public class RestHistoryPermissionController {
    private HistoryPermissionService hpService;

    @GetMapping
    public List<HistoryPermissionResponse> getAll(){
        return hpService.getAll();
    }

    @GetMapping("/{id}")
    public HistoryPermission getById(@PathVariable Integer id){
        return hpService.getById(id);
    }

}
