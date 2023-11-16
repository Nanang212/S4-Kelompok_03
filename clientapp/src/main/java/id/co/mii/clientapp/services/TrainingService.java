package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.Training;
import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.models.dto.request.TrainingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrainingService {
  @Value("${server.base.url}/trainings")
  private String url;
  @Autowired
  private RestTemplate restTemplate;

  public List<Training> getAll(Integer categoryId) {
    return restTemplate
        .exchange(url.concat("?categoryId=" + (categoryId == null ? "" : categoryId)), HttpMethod.GET, null, new ParameterizedTypeReference<List<Training>>() {
        })
        .getBody();
  }

  public List<Training> getAllByTrainee(String username) {
    return restTemplate
        .exchange(url.concat("/trainee/" + username), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Training>>() {
            })
        .getBody();
  }

  public List<Training> getAllByTrainer(String username) {
    return restTemplate
            .exchange(url.concat("/trainer/" + username), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Training>>() {
                    })
            .getBody();
  }

  public Training getById(Integer id) {
    return restTemplate
        .exchange(url.concat("/" + id), HttpMethod.GET, null, Training.class)
        .getBody();
  }

  public Training create(TrainingRequest trainingRequest) {
    return restTemplate
        .exchange(url, HttpMethod.POST, new HttpEntity<TrainingRequest>(trainingRequest), Training.class)
        .getBody();
  }

  public Training broadcast(Integer id) {
    return restTemplate
            .exchange(url.concat("/broadcast/" + id), HttpMethod.POST, null, Training.class)
            .getBody();
  }

  public Training update(Integer id, TrainingRequest trainingRequest) {
  HttpEntity<TrainingRequest> request = new HttpEntity<>(trainingRequest);
  return restTemplate
  .exchange(url.concat("/" + id), HttpMethod.PUT, request, Training.class)
  .getBody();
  }


  public void delete(Integer id) {
    restTemplate
        .exchange(url.concat("/" + id), HttpMethod.DELETE, null, Training.class)
        .getBody();
  }
}
