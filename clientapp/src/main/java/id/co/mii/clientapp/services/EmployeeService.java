// package id.co.mii.clientapp.services;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpMethod;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// // import id.co.mii.clientapp.models.Country;
// import id.co.mii.clientapp.models.Employee;
// // import id.co.mii.clientapp.request.CountryRequest;
// import id.co.mii.clientapp.request.EmployeeRequest;
// import id.co.mii.clientapp.response.EmployeeResponse;
// import id.co.mii.clientapp.utils.BasicHeaderUtil;


// @Service
// public class EmployeeService {

//     @Value("${server.base.url}/employee")
//     private String url;

//     @Autowired
//     private BasicHeaderUtil headers; 

//     @Autowired
//     private RestTemplate restTemplate;

//     public List<Employee> getAll() {
//         return restTemplate
//                 .exchange(
//                         url,
//                         HttpMethod.GET,
//                          new HttpEntity<>(headers.createBasicHeader()),
//                         // null,
//                         new ParameterizedTypeReference<List<Employee>>() {
//                         })
//                 .getBody();
//     }

//     public Employee getById(Integer employeeId) {
//         return restTemplate.exchange(
//                 url.concat("/" + employeeId),
//                 HttpMethod.GET,
//                 // null,
//                 new HttpEntity<>(employeeId , headers.createBasicHeader()),
//                 new ParameterizedTypeReference<Employee>() {
//                 })
//                 .getBody();
//     }

//     public Employee registration(EmployeeRequest employeeRequest) {
//         return restTemplate
//                 .exchange(url.concat("/registration"),
//                  HttpMethod.POST,
//                 new HttpEntity<>(employeeRequest, headers.createBasicHeader()),
//                 Employee.class)
//                 .getBody();
//     }

//     public Employee update(Integer id, EmployeeRequest employeeRequest) {
//         return restTemplate
//                 .exchange(url.concat("/" + id),
//                  HttpMethod.PUT, 
//                  new HttpEntity<>(employeeRequest, headers.createBasicHeader()),
//                   Employee.class)
//                 .getBody();
//     }

//     public Employee delete(Integer employeeId) {
//         return restTemplate.exchange(
//                 url.concat("/" + employeeId),
//                 HttpMethod.DELETE,
//                  new HttpEntity<>(headers.createBasicHeader()),
//                 // null,
//                 // Employee.class)
                
//                  new ParameterizedTypeReference<Employee>() {
//                 })
//                 .getBody();
    
// }

// public Employee findByUsername(String username){
//         return restTemplate.exchange(
//         url.concat("/user?username=" + username),
//         HttpMethod.GET,
//         null,
//         Employee.class  
//         ).getBody();
//     }
// }


// // @Service
// // public class EmployeeService {
// //   @Autowired
// //   private RestTemplate restTemplate;
// //   @Value("${server.base.url}/employee/")
// //   private String url;

// //   public List<Employee> getAll() {
// //     return restTemplate.exchange(
// //             url,
// //             HttpMethod.GET,
// //             null,
// //             new ParameterizedTypeReference<List<Employee>>() {}
// //     ).getBody();
// //   }

// //   public Employee getById(Integer id) {
// //     return restTemplate.exchange(
// //             url.concat(id.toString()),
// //             HttpMethod.GET,
// //             null,
// //             new ParameterizedTypeReference<Employee>() {}
// //     ).getBody();
// //   }

// //   public Employee create(Employee employee) {
// //     return restTemplate.exchange(
// //             url,
// //             HttpMethod.POST,
// //             new HttpEntity<>(employee),
// //             new ParameterizedTypeReference<Employee>() {}
// //     ).getBody();
// //   }

// //   public Employee create(EmployeeRequest employeeRequest) {
// //     return restTemplate.exchange(
// //             url.concat("/dto"),
// //             HttpMethod.POST,
// //             new HttpEntity<>(employeeRequest),
// //             new ParameterizedTypeReference<Employee>() {}
// //     ).getBody();
// //   }
// //   public EmployeeResponse update(Integer id, EmployeeRequest employeeRequest) {
// //     return restTemplate.exchange(
// //             url.concat(id.toString()),
// //             HttpMethod.PUT,
// //             new HttpEntity<>(employeeRequest),
// //             new ParameterizedTypeReference<EmployeeResponse>() {}
// //     ).getBody();
// //   }

// //   public Employee delete(Integer id) {
// //     return restTemplate.exchange(
// //             url.concat(id.toString()),
// //             HttpMethod.DELETE,
// //             null,
// //             new ParameterizedTypeReference<Employee>() {}
// //     ).getBody();
// //   }
// // }