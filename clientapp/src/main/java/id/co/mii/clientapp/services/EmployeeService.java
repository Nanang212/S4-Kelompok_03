package id.co.mii.clientapp.services;

import java.util.List;

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

    public List<Employee> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        }).getBody();
    }

    // public List<Employee> getAllByRoleAdmin() {
    //     // Ganti URL sesuai dengan endpoint yang sesuai dengan role admin
    //     String adminUrl = url + "/admin";
    //     return restTemplate.exchange(adminUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
    //     }).getBody();
    // }

    // public List<Employee> getAllByRoleTrainer() {
    //     // Ganti URL sesuai dengan endpoint yang sesuai dengan role trainer
    //     String trainerUrl = url + "/trainer";
    //     return restTemplate.exchange(trainerUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
    //     }).getBody();
    // }

    // public List<Employee> getAllByRoleTrainee() {
    //     // Ganti URL sesuai dengan endpoint yang sesuai dengan role trainee
    //     String traineeUrl = url + "/trainee";
    //     return restTemplate.exchange(traineeUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
    //     }).getBody();
    // }

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

    public EmployeeResponse delete(Integer id) {
        return restTemplate.exchange(url.concat("/" + id), HttpMethod.DELETE, null, EmployeeResponse.class)
                .getBody();
    }
}
