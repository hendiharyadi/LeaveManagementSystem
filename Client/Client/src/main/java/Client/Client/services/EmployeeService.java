package Client.Client.services;

import Client.Client.models.dto.EmployeeResponse;
import Client.Client.models.dto.StockResponse;
import Client.Client.models.dto.UserRegistrationDto;
import Client.Client.models.entities.Employee;
import Client.Client.util.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {

    private RestTemplate restTemplate;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/employee")
    private String url;

    public List<Employee> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Employee>>(){
                }).getBody();
    }

    public List<EmployeeResponse> getMyStaff(){
        return restTemplate.exchange(url + "/manager/list-staff", HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<EmployeeResponse>>(){
                }).getBody();
    }

    public StockResponse getStock(){
        System.out.println("Stock");
        return restTemplate.exchange("http://localhost:8081/api/employee/stock-leave", HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<StockResponse>(){
                }).getBody();
    }

    public Employee getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee employeeLogin(){
        return restTemplate.exchange(url + "/dashboard" , HttpMethod.GET, null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee create(UserRegistrationDto employee) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(employee),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee update(int id, UserRegistrationDto employee) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(employee),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public String delete(int id) {
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
    }
}
