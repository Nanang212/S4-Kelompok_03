package id.co.mii.serverapp.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchKeyWord {
  private String name;
  private String phone;
  private String email;
  private String address;
  private String jobPosition;
  private String username;
  private Integer roleId;
}
