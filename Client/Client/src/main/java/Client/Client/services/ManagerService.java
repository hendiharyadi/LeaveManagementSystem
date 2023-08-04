package Client.Client.services;

import Client.Client.models.entities.Employee;
import Client.Client.util.BasicHeader;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {
    private RestTemplate restTemplate;

    public List<Employee> getManagers(){
        return restTemplate.exchange(
                "http://localhost:8081/api/role/managers",
                HttpMethod.GET,
                new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Employee>>() {
                }
        ).getBody();
    }
}
