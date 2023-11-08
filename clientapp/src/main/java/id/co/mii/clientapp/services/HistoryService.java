package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.History;
import id.co.mii.clientapp.models.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
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
}
