package Client.Client.controllers.api;

import Client.Client.models.dto.EmployeeResponse;
import Client.Client.models.dto.StockResponse;
import Client.Client.models.dto.UserRegistrationDto;
import Client.Client.models.entities.Employee;
import Client.Client.services.EmployeeService;
import Client.Client.services.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeRestController {
    private ManagerService managerService;
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }

    @GetMapping("/dashboard")
    public Employee getUserLoginData(){
        return employeeService.employeeLogin();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id){
        return employeeService.getById(id);
    }

    @GetMapping("/managers")
    public List<Employee> getAllManagers(){
        return managerService.getManagers();
    }

    @GetMapping("/stock-leave")
    public StockResponse getStockLeave(){
        return employeeService.getStock();
    }

    @GetMapping("/manager/list-staff")
    public List<EmployeeResponse> getStaff(){
        return employeeService.getMyStaff();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Employee create(@RequestBody UserRegistrationDto employee){
        return employeeService.create(employee);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping
    public Employee update(@PathVariable Integer id, @RequestBody UserRegistrationDto employee){
        return employeeService.update(id, employee);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        return employeeService.delete(id);
    }
}
