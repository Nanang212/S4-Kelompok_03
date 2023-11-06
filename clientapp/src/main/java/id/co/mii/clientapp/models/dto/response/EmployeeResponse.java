package id.co.mii.clientapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String jobPosition;
    private String username;
}
