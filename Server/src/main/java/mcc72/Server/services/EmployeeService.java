package mcc72.Server.services;

import javafx.beans.binding.ObjectExpression;
import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.EmployeeProjectDto;
import mcc72.Server.models.dto.StockResponse;
import mcc72.Server.models.dto.UserDto;
import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Permission;
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

import java.security.Security;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Employee> findAll(){
        if(employeeRepository.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data Available.");
        }
        return employeeRepository.findAll();
    }

    public Map<String, Object> getEmployee(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()). get();
        Map<String, Object> m = new HashMap<>();
        Employee employee = user.getEmployee();
        m.put("id", employee.getId());
        m.put("first_name", employee.getFirst_name());
        m.put("last_name", employee.getLast_name());
        m.put("email", employee.getEmail());
        m.put("phone_number", employee.getPhone_number());
        m.put("user", employee.getUser());
        m.put("manager", employee.getManager());
        m.put("managers", employee.getManagers());
        m.put("employeeProject", employee.getEmployeeProject());
        m.put("stockLeave", employee.getStockLeave());
        m.put("overtimes", employee.getOvertimes());
        m.put("permissions", employee.getPermissions());
        m.put("projects", employee.getProjects());
        return m;
    }

    public List<Map<String, Object>> getAllMap(){
        return employeeRepository.findAll().stream().map(employee -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", employee.getId());
            m.put("first_name", employee.getFirst_name());
            m.put("last_name", employee.getLast_name());
            m.put("email", employee.getEmail());
            m.put("phone_number", employee.getPhone_number());
            m.put("user", employee.getUser());
            m.put("manager", employee.getManager());
            m.put("managers", employee.getManagers());
            m.put("employeeProject", employee.getEmployeeProject());
            m.put("stockLeave", employee.getStockLeave());
            m.put("overtimes", employee.getOvertimes());
            m.put("permissions", employee.getPermissions());
            m.put("projects", employee.getProjects());
            return m;
        }).collect(Collectors.toList());
    }

    public Object findById(Integer id){
        if(!employeeRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data is not existed.");
        }
        Employee employee = employeeRepository.findById(id).get();
        Map<String, Object> m = new HashMap<>();
        m.put("id", employee.getId());
        m.put("first_name", employee.getFirst_name());
        m.put("last_name", employee.getLast_name());
        m.put("email", employee.getEmail());
        m.put("phone_number", employee.getPhone_number());
        m.put("user", employee.getUser());
        m.put("manager", employee.getManager());
        m.put("managers", employee.getManagers());
        m.put("employeeProject", employee.getEmployeeProject());
        m.put("stockLeave", employee.getStockLeave());
        m.put("overtimes", employee.getOvertimes());
        m.put("permissions", employee.getPermissions());
        m.put("projects", employee.getProjects());
        return m;
    }

    public Employee insert(UserDto e){
        if(employeeRepository.findByEmail(e.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.FOUND, "Data is existed.");
        }
        Employee employee = new Employee();
        employee.setFirst_name(e.getFirst_name());
        employee.setLast_name(e.getLast_name());
        employee.setEmail(e.getEmail());
        employee.setPhone_number(e.getPhone_number());
        if(e.getManager_id() != null){
            employee.setManager(employeeRepository.findById(e.getManager_id()).get());
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Integer id, UserDto e){
        findById(id);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirst_name(e.getFirst_name());
        employee.setLast_name(e.getLast_name());
        employee.setEmail(e.getEmail());
        employee.setPhone_number(e.getPhone_number());
        if (e.getManager_id() != null){
            employee.setManager(employeeRepository.findById(e.getManager_id()).get());
        }
        return employeeRepository.save(employee);
    }

    public String deleteById(Integer id){
        Employee e = employeeRepository.findById(id).get();
        employeeRepository.delete(e);
        return "Success";
    }

    public List<Employee> findMyStaff(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User manager = userRepository.findByUsername(auth.getName()).get();
        return manager.getEmployee().getManagers();
    }

    public List<Permission> findMyStaffPermission(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User manager = userRepository.findByUsername(auth.getName()).get();
        return manager.getEmployee().getPermissions();
    }

    public Object addEmployeeToProject(EmployeeProjectDto empDto) {
        Project project = projectRepository.findById(empDto.getProject_id()).get();
        Employee employee = employeeRepository.findById(empDto.getEmployee_id()).get();
        project.setEmployeeProject(Collections.singletonList(employee));
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("employee", employee);
        map.put("employee project", employee.getEmployeeProject());
        return map;
    }

    public StockResponse getUserStockLeave(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User manager = userRepository.findByUsername(auth.getName()).get();
        StockResponse stockResponse = new StockResponse();
        stockResponse.setStock_available(manager.getEmployee().getStockLeave().getStock_available());
        return stockResponse;
    }
}
