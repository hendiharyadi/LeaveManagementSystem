package Client.Client.util;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if(!request.getURI().getPath().equals("/api/login")){
            request.getHeaders().add("Authorization", "Basic" + BasicHeader.createHeaders());
        }
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }
}