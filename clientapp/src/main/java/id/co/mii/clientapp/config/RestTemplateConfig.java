package id.co.mii.clientapp.config;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import id.co.mii.clientapp.utils.RequestInterceptorUtil;
import lombok.AllArgsConstructor;

@Configuration 
@AllArgsConstructor 
public class RestTemplateConfig {

    private AuthenticationSessionUtil session;
    // @Bean
    // public RestTemplate restTemplate(){
    //     return new RestTemplate(){{
    //         List<ClientHttpRequestInterceptor> interceptors = getInterceptors();
    //         if (CollectionUtils.isEmpty(interceptors)) {
    //             interceptors = new ArrayList<>();
    //         }
    //         interceptors.add(new RestTemplateHeaderModifierInterceptor());
    //         setInterceptors(interceptors);
    //     }};
    // }
    // public static class RestTemplateHeaderModifierInterceptor
    //         implements ClientHttpRequestInterceptor {

    //     @Override
    //     public ClientHttpResponse intercept(
    //             HttpRequest request,
    //             byte[] body,
    //             ClientHttpRequestExecution execution) throws IOException {

    //         System.out.println("HARUSNYA DIPANGGIL TIAP  REQ");

    //         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    //         if (!(authentication instanceof AnonymousAuthenticationToken)&& authentication != null) {

    //         byte[] encode = Base64.getEncoder().encode(String.format("%s:%s", authentication.getName(), authentication.getCredentials()).getBytes());
    //         System.out.println(Arrays.toString(encode));
    //         request.getHeaders().add("Authorization", "Basic " + new String(encode, StandardCharsets.UTF_8)); //

    //         }

    //         ClientHttpResponse response = execution.execute(request, body);

    //         return response;
    //     }
    // }

    @Bean
    public RestTemplate restTemplate(){
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

        // return new RestTemplate();

