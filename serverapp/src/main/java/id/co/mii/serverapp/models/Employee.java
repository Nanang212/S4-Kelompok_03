package id.co.mii.serverapp.models;

import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
  @Column(nullable = false, length = 50)
  private String name;
  @Column(unique = true, length = 15)
  private String phone;
  @Column(unique = true, nullable = false, length = 50)
  private String email;
  private String address;
  private String jobPosition;
  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private User user;
}
