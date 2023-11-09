package id.co.mii.clientapp.services;

import java.util.List;

import id.co.mii.clientapp.models.dto.request.PasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.models.dto.response.EmployeeResponse;

@Service
public class EmployeeService {
    @Value("${server.base.url}/employees")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public Employee getLoggedInUser() {
        return restTemplate.exchange(
                url.concat("/current"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Employee>() {}
        ).getBody();
    }

    public List<Employee> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }).getBody();
    }

     public List<Employee> getAllBy(String role) {
         String trainerUrl = url + "/" + role;
         return restTemplate
                 .exchange(trainerUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
         }).getBody();
     }

    public Employee getById(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, null, Employee.class)
                .getBody();
    }

    public Employee create(EmployeeRequest employeeRequest) {
        ResponseEntity<Employee> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(employeeRequest),
                Employee.class);
        return response.getBody();
    }

    public Employee update(Integer id, EmployeeRequest employeeRequest) {
        ResponseEntity<Employee> response = restTemplate.exchange(url.concat("/" + id), HttpMethod.PUT,
                new HttpEntity<>(employeeRequest), Employee.class);
        return response.getBody();
    }

    public Employee editProfile(Integer id, EmployeeRequest employeeRequest) {
        ResponseEntity<Employee> response = restTemplate.exchange(url.concat("/profile/" + id), HttpMethod.PUT,
                new HttpEntity<>(employeeRequest), Employee.class);
        return response.getBody();
    }

    public Employee changePassword(PasswordRequest passwordRequest) {
        ResponseEntity<Employee> response = restTemplate
                .exchange(
                        url.concat("/change-password"),
                        HttpMethod.PUT,
                        new HttpEntity<>(passwordRequest),
                        Employee.class);
        return response.getBody();
    }

    // public Employee update(Integer id, EmployeeRequest employeeRequest) {
    //     HttpEntity<Employee> request = new HttpEntity<>(employeeRequest);
    //     return restTemplate
    //       .exchange(url.concat("/" + id), HttpMethod.PUT, request, Employee.class)
    //       .getBody();
    //   }

    public EmployeeResponse delete(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null, EmployeeResponse.class)
                .getBody();
    }
}
