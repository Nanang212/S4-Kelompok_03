package id.co.mii.clientapp.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// import id.co.mii.clientapp.models.Employee;
// import id.co.mii.clientapp.request.EmployeeRequest;



@Service
public class EmailRegistrationService {
    
     @Value("${server.base.url}/email")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    // public Employee registration(EmployeeRequest request){
    //     Employee employee = restTemplate.exchange(
    //         url.concat("/registration"), 
    //         HttpMethod.POST,
    //         new HttpEntity<EmployeeRequest>(request), 
    //         Employee.class
    //         ).getBody();

    //         System.out.println(employee);
    //     return employee;
    // }

    public String isVerifyUser(String token){
      String api = restTemplate.exchange(
            url.concat("/verify?token=" + token),
            HttpMethod.GET,
            null,
            String.class
      ).getBody();
      System.out.println(api);
      return api;
    }
}