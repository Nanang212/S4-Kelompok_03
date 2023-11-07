package id.co.mii.clientapp.configs;

import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import id.co.mii.clientapp.utils.RequestInterceptorUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class RestTemplateConfig {

  private AuthenticationSessionUtil session;

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new RequestInterceptorUtil(session));
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }
}
