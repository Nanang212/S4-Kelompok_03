package id.co.mii.clientapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Survey;

@Service

public class SurveyService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.base.url}/survey")
    private String url;

    public List<Survey> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Survey>>() {
        }).getBody();
    }

    public Survey getById(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null, Survey.class)
                .getBody();
    }

    public Survey create(Survey survey) {
        return restTemplate
                .exchange(url, HttpMethod.POST, new HttpEntity<>(survey), new ParameterizedTypeReference<Survey>() {
                }).getBody();
    }
}
