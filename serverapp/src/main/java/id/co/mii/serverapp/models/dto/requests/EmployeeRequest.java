package id.co.mii.serverapp.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
