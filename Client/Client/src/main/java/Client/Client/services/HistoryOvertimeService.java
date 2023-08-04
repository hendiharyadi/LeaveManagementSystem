package Client.Client.services;

import Client.Client.models.dto.HistoryOvertimeResponse;
import Client.Client.models.entities.HistoryOvertime;
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
public class HistoryOvertimeService {
    private RestTemplate restTemplate;

    @Autowired
    public HistoryOvertimeService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/history-overtime")
    public String url;

    public List<HistoryOvertimeResponse> getAll(){
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(BasicHeader.createHeaders()),
                new ParameterizedTypeReference<List<HistoryOvertimeResponse>>() {
                }).getBody();
    }

    public HistoryOvertimeResponse getById(int id){
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<HistoryOvertimeResponse>() {
                }).getBody();
    }
}
