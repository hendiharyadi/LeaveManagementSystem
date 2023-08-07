package Client.Client.services;

import Client.Client.models.dto.OvertimeDto;
import Client.Client.models.entities.Overtime;
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
public class OvertimeService {

    private RestTemplate restTemplate;

    @Autowired
    public OvertimeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/overtime")
    private String url;

    public List<Overtime> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Overtime>>(){
                }).getBody();
    }


    public List<Overtime> getAllByManager(){
        return restTemplate.exchange(url + "/manager", HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Overtime>>(){
                }).getBody();
    }

    public Overtime getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Overtime>() {
                }).getBody();
    }

    public Overtime create(OvertimeDto overtime) {
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(overtime),
                new ParameterizedTypeReference<Overtime>() {
                }).getBody();
    }

    public Overtime update(int id, OvertimeDto overtime) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(overtime),
                new ParameterizedTypeReference<Overtime>() {
                }).getBody();
    }

    public Overtime delete(int id) {
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Overtime>() {
                }).getBody();
    }
}
