package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.History;
import id.co.mii.clientapp.models.dto.response.HistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/histories/")
    private String url;

    public List<History> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<History>>() {}
        ).getBody();
    }

    public List<HistoryResponse> getAllByTraining() {
        return restTemplate.exchange(
                url.concat("training"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<HistoryResponse>>() {}
        ).getBody();
    }

    public List<History> getHistoryDetails() {
        // Modifikasi ini sesuai dengan kebutuhan Anda
        return restTemplate.exchange(
                url + "details",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<History>>() {}
        ).getBody();
    }
}
