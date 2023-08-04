package Client.Client.services;

import Client.Client.models.dto.ProjectDto;
import Client.Client.models.dto.ProjectResponse;
import Client.Client.models.entities.Employee;
import Client.Client.models.entities.Project;
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
public class ProjectService {

    private RestTemplate restTemplate;

    @Autowired
    public ProjectService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/project")
    private String url;

    public List<ProjectResponse> getAll(){
        return restTemplate.exchange(url + "/manager", HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<ProjectResponse>>() {
                }).getBody();
    }

    public List<Employee> getAllMembers(Integer id){
        return restTemplate.exchange(url + "/members/" + id, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<Employee>>() {
                }).getBody();
    }

    public ProjectResponse getById(Integer id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<ProjectResponse>() {
                }).getBody();
    }

    public Project create(ProjectDto project){
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }

    public Project update(Integer id, ProjectDto project){
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity(project),
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }

    public Project delete(Integer id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Project>() {
                }).getBody();
    }
}
