package Client.Client.controllers.api;

import Client.Client.models.dto.ProjectDto;
import Client.Client.models.dto.ProjectResponse;
import Client.Client.models.entities.Employee;
import Client.Client.models.entities.Project;
import Client.Client.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
public class RestProjectController {

    private ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> getAll(){
        return projectService.getAll();
    }

    @GetMapping("/members/{id}")
    public List<Employee> getAllMembers(@PathVariable Integer id){
        return projectService.getAllMembers(id);
    }

    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_MANAGER')")
    @GetMapping("/{id}")
    public ProjectResponse getById(@PathVariable Integer id){
        return projectService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_MANAGER')")
    @PostMapping
    public Project create (@RequestBody ProjectDto project){
        return projectService.create(project);
    }

    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("/{id}")
    public Project update (@PathVariable Integer id, @RequestBody ProjectDto project){
        return projectService.update(id, project);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public Project delete (@PathVariable Integer id){
        return projectService.delete(id);
    }
}
