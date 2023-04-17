package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.entity.HistoryPermission;
import mcc72.Server.services.HistoryPermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/history-permission")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_USER')")
public class HistoryPermissionController {

    private HistoryPermissionService hps;

    @GetMapping()
    public List<HistoryPermission> getHistoryPermission() {
        return hps.getAll();
    }

    @GetMapping("/{id}")
    public HistoryPermission getDetail(@PathVariable int id){
        return hps.getById(id);
    }
}
