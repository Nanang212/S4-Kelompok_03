package id.co.mii.clientapp.models.dto.request;

import java.util.Collections;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
  private String name;
  private String phone;
  private String email;
  private String address;
  private String jobPosition;
  private String username;
  private String password;
  private Set<Integer> roleIds;
}
