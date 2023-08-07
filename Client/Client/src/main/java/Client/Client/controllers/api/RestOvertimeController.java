package Client.Client.controllers.api;

import Client.Client.models.dto.OvertimeDto;
import Client.Client.models.entities.Overtime;
import Client.Client.services.OvertimeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overtime")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class RestOvertimeController {

    private OvertimeService overtimeService;

    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public List<Overtime> getAll(){
        return overtimeService.getAll();
    }

    @GetMapping("/manager")
    public List<Overtime> getAllByManager(){
        return overtimeService.getAllByManager();
    }

    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public Overtime getById(@PathVariable int id){
        return overtimeService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public Overtime create (@RequestBody OvertimeDto overtime){
        return overtimeService.create(overtime);
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Overtime update (@PathVariable int id, @RequestBody OvertimeDto overtime){
        return overtimeService.update(id, overtime);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Overtime delete (@PathVariable int id){
        return overtimeService.delete(id);
    }
}
