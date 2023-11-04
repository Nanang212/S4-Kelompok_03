package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.requests.EmployeeRequest;
import id.co.mii.serverapp.models.dto.requests.LoginRequest;
import id.co.mii.serverapp.models.dto.responses.LoginResponse;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import id.co.mii.serverapp.repositories.UserRepository;
import id.co.mii.serverapp.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
  private UserRepository userRepository;
  private EmployeeRepository employeeRepository;
  private ModelMapper modelMapper;
  private RoleService roleService;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private AppUserDetailsService userDetailsService;

  public Employee registration(EmployeeRequest employeeRequest) {
    if (!StringUtils.isEmptyOrNull(employeeRequest.getUsername())) {
      if (userRepository.existsByUsername(employeeRequest.getUsername())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already used !!!");
      }
    }
    if (!StringUtils.isEmptyOrNull(employeeRequest.getEmail())) {
      if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already used !!!");
      }
    }
    if (!StringUtils.isEmptyOrNull(employeeRequest.getPhone())) {
      if (employeeRepository.existsByPhone(employeeRequest.getPhone())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already used !!!");
      }
    }

    Employee employee = modelMapper.map(employeeRequest, Employee.class);
    User user = modelMapper.map(employeeRequest, User.class);

    if (!StringUtils.isEmptyOrNull(employeeRequest.getPassword())) {
      user.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
    }

    user.setEmployee(employee);
    user.setRoles(mapToRoles(employeeRequest.getRoleIds()));
    employee.setUser(user);

    return employeeRepository.save(employee);
  }

  public LoginResponse login(LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    Authentication authentication = authenticationManager.authenticate(authReq);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = userRepository.findByUsernameOrEmployeeEmail(loginRequest.getUsername(), loginRequest.getUsername()).get();
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

    List<String> authorities = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return new LoginResponse(user.getUsername(), user.getEmployee().getEmail(), authorities);
  }

  private Set<Role> mapToRoles(Set<Integer> roleIds) {
    return roleIds
            .stream()
            .map(roleId -> roleService.getById(roleId))
            .collect(Collectors.toSet());
  }
}
