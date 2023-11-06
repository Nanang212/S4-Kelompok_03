// package id.co.mii.clientapp.services;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// // import id.co.mii.clientapp.models.Employee;
// // import id.co.mii.clientapp.request.EmployeeRequest;
// import id.co.mii.clientapp.request.LoginRequest;
// import id.co.mii.clientapp.request.RegisterRequest;
// import id.co.mii.clientapp.request.RegistrationRequest;
// // import id.co.mii.clientapp.response.EmployeeResponse;
// import id.co.mii.clientapp.response.LoginResponse;
// import id.co.mii.clientapp.utils.AuthenticationSessionUtil;

// @Service
// public class AuthService {

//   @Value("${server.base.url}")
//   private String url;

//   @Autowired
//   private RestTemplate restTemplate;

//   @Autowired
//   private AuthenticationSessionUtil session;

//     public Boolean login(LoginRequest loginRequest) {
//     try {
//       ResponseEntity<LoginResponse> response = restTemplate.exchange(
//         url.concat("/login"),
//         HttpMethod.POST,
//         new HttpEntity<>(loginRequest),
//         LoginResponse.class
//       );
//       if (response.getStatusCode() == HttpStatus.OK) {
//         setPrinciple(response.getBody(), loginRequest.getPassword());

//         // Authentication auth = SecurityContextHolder
//         //   .getContext()
//         //   .getAuthentication();
//         System.out.println(
//           "\nYang masuk ini ni -> " + session.authentication().getName() + "...\n"
//         );
//         System.out.println(
//           "\nYang masuk ini ni -> " + session.authentication().getAuthorities().toString() + "...\n"
//         );
//         return true;
//       }
//     } catch (Exception e) {
//       System.out.println("Error = " + e);
//       return false;
//     }
//     return false;
//   }

//   public void setPrinciple(LoginResponse response, String password) {
//     List<SimpleGrantedAuthority> authorities = response
//       .getAuthorities()
//       .stream()
//       .map(authority -> new SimpleGrantedAuthority(authority))
//       .collect(Collectors.toList());

//     UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//       response.getUsername(),
//       password,
//       authorities
//     );

//     // set principle
//     SecurityContextHolder.getContext().setAuthentication(token);
//   }


//   // @Value("${server.base.url}/api/registration/")
//   // private String url;

// //   public EmployeeResponse registration(EmployeeRequest employeeRequest) {
// //     return restTemplate.exchange(
// //             url.concat("registration"),
// //             HttpMethod.POST,
// //             new HttpEntity<>(employeeRequest),
// //             new ParameterizedTypeReference<EmployeeResponse>() {}
// //     ).getBody();
// //   }
// // }

// // public Employee registration(RegisterRequest registrationRequest) {

// //         try {
// //             Employee employee = restTemplate.exchange(
// //                 url.concat("/registration"), 
// //                 HttpMethod.
// //                 POST,
// //                 new HttpEntity<>(registrationRequest), new ParameterizedTypeReference<Employee>() {
// //                 }).getBody();

// //             return employee;
// //         } catch (Exception e) {
// //             System.out.println("Error " + e);
// //             return new Employee();
// //         }
// //     }
// }

