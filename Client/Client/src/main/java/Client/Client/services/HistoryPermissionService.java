package Client.Client.services;

import Client.Client.models.dto.HistoryPermissionResponse;
import Client.Client.models.entities.HistoryPermission;
import Client.Client.util.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.websocket.RemoteEndpoint;
import java.util.List;

@Service
public class HistoryPermissionService {

    private RestTemplate restTemplate;

    @Autowired
    public HistoryPermissionService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/history-permission")
    private String url;

    public List<HistoryPermissionResponse> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<HistoryPermissionResponse>>(){
                }).getBody();
    }

    public HistoryPermission getById(Integer id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<HistoryPermission>() {
                }).getBody();
    }
}
