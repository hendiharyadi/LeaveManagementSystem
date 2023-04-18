package mcc72.Server.controllers;

import lombok.AllArgsConstructor;
import mcc72.Server.models.dto.EmployeeProjectDto;
import mcc72.Server.models.dto.StockResponse;
import mcc72.Server.models.dto.UserDto;
import mcc72.Server.models.entity.Employee;
import mcc72.Server.models.entity.Permission;
import mcc72.Server.services.EmployeeService;
import mcc72.Server.services.StockLeaveService;
import mcc72.Server.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("employee")
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
public class EmployeeController {

    private EmployeeService employeeService;
    private UserService userEntityService;
    private StockLeaveService stockLeaveService;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<Map<String, Object>> getAllMap() {
        return employeeService.getAllMap();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/dashboard")
    public Map<String, Object> getEmployee(){
        return employeeService.getEmployee();
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Employee insert(@RequestBody UserDto employee) {
        Employee e = employeeService.insert(employee);
        userEntityService.insert(employee);
        stockLeaveService.create(employee);
        userEntityService.senderVerifyMail(employee);
        return e;
    }

    @PutMapping("/add-project")
    public Object addProject(@RequestBody EmployeeProjectDto employeeProjectDto){
        return employeeService.addEmployeeToProject(employeeProjectDto);
    }

    @GetMapping("/stock-leave")
    public StockResponse getStock(){
        return employeeService.getUserStockLeave();
    }

    @GetMapping("/manager/list-staff")
    public List<Employee> findStaff(){
        return employeeService.findMyStaff();
    }

    @GetMapping("/manager/list-permission-staff")
    public List<Permission> findMyStaffPermission(){
        return employeeService.findMyStaffPermission();
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public Object getById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody UserDto employee) {
        return employeeService.update(id, employee);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }
}
