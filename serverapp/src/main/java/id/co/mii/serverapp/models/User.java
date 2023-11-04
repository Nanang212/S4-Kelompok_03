package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
  @Column(unique = true)
  private String username;
  private String password;
  @OneToOne
  @MapsId
  @JoinColumn(name = "employee")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Employee employee;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;
}
