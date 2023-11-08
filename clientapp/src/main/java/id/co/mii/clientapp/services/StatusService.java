package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StatusService {
  @Autowired
  private RestTemplate restTemplate;
  @Value("${server.base.url}/status/")
  private String url;

  public List<Status> getAll() {
    return restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Status>>() {}
    ).getBody();
  }

  public Status create(Status status) {
    return restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(status),
            new ParameterizedTypeReference<Status>() {}
    ).getBody();
  }

  public Status getById(Integer id) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Status>() {}
    ).getBody();
  }

  public Status update(Integer id, Status status) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.PUT,
            new HttpEntity<>(status),
            new ParameterizedTypeReference<Status>() {}
    ).getBody();
  }

  public Status delete(Integer id) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<Status>() {}
    ).getBody();
  }
}
