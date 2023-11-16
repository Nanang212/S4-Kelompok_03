package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CategoryService {
  @Autowired
  private RestTemplate restTemplate;
  @Value("${server.base.url}/categories")
  private String url;

  public List<Category> getAll() {
    return restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Category>>() {}
    ).getBody();
  }

  public Category getById(Integer id) {
    return restTemplate.exchange(
            url.concat("/" + id),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Category>() {}
    ).getBody();
  }
}
