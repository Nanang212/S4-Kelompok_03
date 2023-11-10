package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.AppUserDetails;
import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.requests.EmployeeRequest;
import id.co.mii.serverapp.models.dto.requests.PasswordRequest;
import id.co.mii.serverapp.models.dto.responses.LoginResponse;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import id.co.mii.serverapp.repositories.UserRepository;
import id.co.mii.serverapp.services.base.BaseService;
import id.co.mii.serverapp.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService extends BaseService<Employee, Integer> {
  private EmployeeRepository employeeRepository;
  private UserRepository userRepository;
  private RoleService roleService;
  private PasswordEncoder passwordEncoder;
  ModelMapper modelMapper;

  public Employee changePassword(PasswordRequest passwordRequest) {
    Employee currentEmployee = getLoggedInEmployee();
    String newPassword = passwordRequest.getNewPassword();
    String oldPassword = passwordRequest.getOldPassword();
    if (!passwordEncoder.matches(oldPassword, currentEmployee.getUser().getPassword())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Bad Credential");
    }
    if (!passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getOldPassword())) {
      currentEmployee.getUser().setPassword(passwordEncoder.encode(newPassword));
      employeeRepository.save(currentEmployee);
    }
    return currentEmployee;
  }

  public Employee getLoggedInEmployee() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
    return findByUsername(userDetails.getUsername());
  }

  public Employee findByUsername(String username) {
    return employeeRepository.findByUserUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
  }

  public List<Employee> getAllByRoleId(Integer roleId) {
    Role role = roleService.getById(roleId);
    return getAll()
            .stream()
            .filter(employee -> employee.getUser().getRoles().contains(role))
            .collect(Collectors.toList());
  }

  public Employee update(Integer id, EmployeeRequest employeeRequest) {
    Employee updatedEmployee = getById(id);

    if (updatedEmployee.getName() == null) {
      updatedEmployee.setName(employeeRequest.getName());
    } else {
      if (!StringUtils.isEmptyOrNull(employeeRequest.getName())
              && !updatedEmployee.getName().equalsIgnoreCase(employeeRequest.getName())) {
        updatedEmployee.setName(employeeRequest.getName());
      }
    }

    if (updatedEmployee.getEmail() == null) {
      if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already used !!!");
      }
      updatedEmployee.setEmail(employeeRequest.getEmail());
    } else {
      if (!StringUtils.isEmptyOrNull(employeeRequest.getEmail())
              && !updatedEmployee.getEmail().equalsIgnoreCase(employeeRequest.getEmail())) {
        if (employeeRepository.existsByEmail(employeeRequest.getEmail())) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already used !!!");
        }
        updatedEmployee.setEmail(employeeRequest.getEmail());
      }
    }

    if (updatedEmployee.getPhone() == null) {
      if (employeeRepository.existsByPhone(employeeRequest.getPhone())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already used !!!");
      }
      updatedEmployee.setPhone(employeeRequest.getPhone());
    } else {
      if (!StringUtils.isEmptyOrNull(employeeRequest.getPhone())
              && !updatedEmployee.getPhone().equalsIgnoreCase(employeeRequest.getPhone())) {
        if (employeeRepository.existsByPhone(employeeRequest.getPhone())) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already used !!!");
        }
        updatedEmployee.setPhone(employeeRequest.getPhone());
      }
    }

    if (updatedEmployee.getJobPosition() == null) {
      updatedEmployee.setJobPosition(employeeRequest.getJobPosition());
    } else {
      if (!StringUtils.isEmptyOrNull(employeeRequest.getJobPosition())
              && !updatedEmployee.getName().equalsIgnoreCase(employeeRequest.getJobPosition())) {
        updatedEmployee.setJobPosition(employeeRequest.getJobPosition());
      }
    }

    if (updatedEmployee.getUser().getUsername() == null) {
      if (userRepository.existsByUsername(employeeRequest.getUsername())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already used !!!");
      }
      updatedEmployee.getUser().setUsername(employeeRequest.getUsername());
    } else {
      if (!StringUtils.isEmptyOrNull(employeeRequest.getUsername())
              && !updatedEmployee.getUser().getUsername().equalsIgnoreCase(employeeRequest.getUsername())) {
        if (userRepository.existsByUsername(employeeRequest.getUsername())) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already used !!!");
        }
        updatedEmployee.getUser().setUsername(employeeRequest.getUsername());
      }
    }

//    if (updatedEmployee.getUser().getPassword() != null) {
//      if (!StringUtils.isEmptyOrNull(employeeRequest.getPassword())
//              && !updatedEmployee.getUser().getPassword().equalsIgnoreCase(employeeRequest.getPassword())) {
//        updatedEmployee.getUser().setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
//      }
//    } else {
//      updatedEmployee.getUser().setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
//    }

    if (employeeRequest.getRoleIds() != null) {
      updatedEmployee.getUser().setRoles(mapToRoles(employeeRequest.getRoleIds()));
    }
    updatedEmployee.setUpdatedAt(LocalDateTime.now());
    updatedEmployee.getUser().setUpdatedAt(LocalDateTime.now());
    return employeeRepository.save(updatedEmployee);
  }

  private Set<Role> mapToRoles(Set<Integer> roleIds) {
    return roleIds
            .stream()
            .map(roleId -> roleService.getById(roleId))
            .collect(Collectors.toSet());
  }
}
