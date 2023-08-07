package Client.Client.services;

import Client.Client.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/user")
    private String url;

    public List<User> getAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic V2lyZGE6d2lyZGE=");
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers),
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }
}
