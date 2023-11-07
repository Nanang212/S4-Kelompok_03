package id.co.mii.clientapp.services;


import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.models.dto.request.LoginRequest;
import id.co.mii.clientapp.models.dto.response.LoginResponse;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

  @Value("${server.base.url}/auth")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AuthenticationSessionUtil session;

  public Boolean login(LoginRequest loginRequest) {
    try {
      ResponseEntity<LoginResponse> response = restTemplate.exchange(
          url.concat("/login"),
          HttpMethod.POST,
          new HttpEntity<>(loginRequest),
          LoginResponse.class);
      if (response.getStatusCode() == HttpStatus.OK) {
        setPrinciple(response.getBody(), loginRequest.getPassword());

        System.out.println(
            "\nYang masuk ini ni -> " +
                session.authentication().getName() +
                "...\n");
        System.out.println(
            "Yang masuk ini ni -> " +
                session.authentication().getAuthorities().toString() +
                "...\n");
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error = " + e);
      return false;
    }
    return false;
  }

  public void setPrinciple(LoginResponse response, String password) {
    List<SimpleGrantedAuthority> authorities = response
        .getAuthorities()
        .stream()
        .map(authority -> new SimpleGrantedAuthority(authority))
        .collect(Collectors.toList());

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        response.getUsername(),
        password,
        authorities);

    // set principle
    SecurityContextHolder.getContext().setAuthentication(token);
  }

  public Employee registration(EmployeeRequest employeeRequest) {

    try {
        Employee employee = restTemplate.exchange(
                url.concat("/registration"),
                HttpMethod.POST,
                new HttpEntity<>(employeeRequest), new ParameterizedTypeReference<Employee>() {
                }).getBody();

        return employee;
    } catch (Exception e) {
        System.out.println("Error " + e);
        return new Employee();
    }
}
}
