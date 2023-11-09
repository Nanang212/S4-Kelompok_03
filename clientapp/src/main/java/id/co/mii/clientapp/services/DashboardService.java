package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.dto.response.DashboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
  @Value("${server.base.url}/dashboard")
  private String url;
  @Autowired
  private RestTemplate restTemplate;

  public DashboardResponse getAll() {
    return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<DashboardResponse>() {
    }).getBody();
  }

  public List<Integer> getTrainingByAllMonthInYear() {
    return restTemplate.exchange(url.concat("/training"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {
    }).getBody();
  }
}
