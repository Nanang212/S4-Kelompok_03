package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
  private String name;
  @ManyToMany(mappedBy = "roles")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<User> users;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "role_privilege",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "privilege_id")
  )
  private Set<Privilege> privileges;
}
