package id.co.mii.clientapp.models;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private Boolean isEnable = true;
    private Employee employee;
    private Set<Role> roles;
}
