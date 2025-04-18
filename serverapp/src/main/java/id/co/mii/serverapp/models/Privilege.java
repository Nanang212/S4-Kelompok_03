package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {
  private String name;
  @ManyToMany(mappedBy = "privileges")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Role> roles;
}
