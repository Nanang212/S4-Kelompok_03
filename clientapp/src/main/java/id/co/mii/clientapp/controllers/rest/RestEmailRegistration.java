package id.co.mii.clientapp.controllers.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.clientapp.controllers.EmailRegistrationService;
import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.request.EmployeeRequest;
// import id.co.mii.clientapp.models.Employee;
// import id.co.mii.clientapp.request.EmployeeRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class RestEmailRegistration {
    
    private EmailRegistrationService emailRegistrationService;

    @PostMapping("/registration")
    public Employee registration(@RequestBody EmployeeRequest request){
     return  emailRegistrationService.registration(request);
    //     // return "auth/login";

    }
}