package mcc72.Server.services;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.ProjectDto;
import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Project;
import mcc72.Server.models.entity.User;
import mcc72.Server.repositories.EmployeeRepository;
import mcc72.Server.repositories.ProjectRepository;
import mcc72.Server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    public List<Project> getAllProject(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return projectRepository.orderProject(user.getEmployee().getId());
    }

    public Object getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return user.getEmployee().getProjects().stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("name", e.getName());
            map.put("start_project", e.getStart_project());
            map.put("end_project", e.getEnd_project());
            map.put("members", e.getEmployeeProject());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Employee> getMemberProject(int id) {
        Project project = projectRepository.findById(id).get();
        return project.getEmployeeProject();
    }

    public Project getById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "History not found..."));
    }

    @Transactional
    public Project create(ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        Project project = new Project();
        project.setManager(user.getEmployee());
        project.setName(projectDto.getName());
        project.setStart_project(projectDto.getStart_project());
        project.setEnd_project(projectDto.getEnd_project());
        List<Employee> employees = new ArrayList<>();
        for(Integer employee : projectDto.getEmployees()){
            employees.add(employeeRepository.findById(employee).get());
        }
        project.setEmployeeProject(employees);
        projectRepository.save(project);
        return project;
    }

    public List<Employee> selectByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        return projectRepository.getEmployeeManager(user.getEmployee().getId());
    }

    public Project update(int id, ProjectDto projectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).get();
        Project project = projectRepository.findById(id).get();
        getById(id);
        project.setName(projectDto.getName());
        return projectRepository.save(project);
    }


    public Project deleteProjectById(int id) {
        Project project = projectRepository.findById(id).get();
        System.out.println("Project ID : " + id);
        projectRepository.deleteById(id);
        return project;
    }
}
