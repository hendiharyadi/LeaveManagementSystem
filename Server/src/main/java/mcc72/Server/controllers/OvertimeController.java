package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.OvertimeDto;
import mcc72.Server.models.entity.Overtime;
import mcc72.Server.services.OvertimeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("overtime")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
public class OvertimeController {

    private OvertimeService os;

    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public Object getAll() {
        return os.getAll();
    }

    @GetMapping("/manager")
    public List<Overtime> findByManager(){
        return os.findByManager();
    }

    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_MANAGER')")
    @PostMapping
    public Overtime create(@RequestBody OvertimeDto overtime){
        Overtime ot = os.create(overtime);
        os.sendRequestMail(overtime);
        return ot;
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Overtime update(@PathVariable Integer id, @RequestBody OvertimeDto overtime){
        Overtime ot = os.update(id, overtime);
        if (overtime.getStatus().equals(true)){
            os.sendConfirmationMail(id, overtime);
        } else {
            os.sendConfirmationMail(id, overtime);
        }
        return ot;
    }

    @PreAuthorize("hasAnyAuthority('READ_MANAGER', 'READ_ADMIN')")
    @GetMapping("/{id}")
    public Overtime getById(@PathVariable int id){
        return os.getById(id);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Overtime delete(@PathVariable int id){
        return os.delete(id);
    }
}
