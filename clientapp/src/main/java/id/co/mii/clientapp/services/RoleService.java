package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoleService {
  @Autowired
  private RestTemplate restTemplate;
  @Value("${server.base.url}/roles/")
  private String url;

  public List<Role> getAll() {
    return restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Role>>() {}
    ).getBody();
  }

  public Role create(Role role) {
    return restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(role),
            new ParameterizedTypeReference<Role>() {}
    ).getBody();
  }

  public Role getById(Integer id) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Role>() {}
    ).getBody();
  }

  public Role update(Integer id, Role role) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.PUT,
            new HttpEntity<>(role),
            new ParameterizedTypeReference<Role>() {}
    ).getBody();
  }

  public Role delete(Integer id) {
    return restTemplate.exchange(
            url.concat(id.toString()),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<Role>() {}
    ).getBody();
  }
}
