package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.ProjectDto;
import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Project;
import mcc72.Server.services.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class ProjectController {

    private ProjectService ps;

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER')")
    @GetMapping
    public List<Project> getAllProject(){
        return ps.getAllProject();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/manager")
    public Object getAll(){
        return ps.getAll();
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public Project getById(@PathVariable int id) {
        return ps.getById(id);
    }

    @GetMapping("/members/{id}")
    public List<Employee> getMembers(@PathVariable int id){
        return ps.getMemberProject(id);
    }

    @PreAuthorize("hasAuthority('CREATE_MANAGER')")
    @PostMapping
    public Project create(@RequestBody ProjectDto projectDto){
        return ps.create(projectDto);
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Project update(@PathVariable int id, @RequestBody ProjectDto projectDto){
        return ps.update(id, projectDto);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Project deleteProject(@PathVariable int id) {
        return ps.deleteProjectById(id);
    }
}
