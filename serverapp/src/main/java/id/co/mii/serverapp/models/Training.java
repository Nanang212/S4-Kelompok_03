package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
// TODO : Ubah relasi training - trainee menjadi many to many tipe ke 3
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training")
public class Training extends BaseEntity {
  private String title;
  private Date startDate;
  private Date endDate;
  private Integer quota;
  private String address;
  private String platformUrl;
  private Boolean isOnline;
  private Integer duration;
  @ManyToOne
  @JoinColumn(name = "trainer")
  private Employee trainer;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "training_register",
          joinColumns = @JoinColumn(name = "training"),
          inverseJoinColumns = @JoinColumn(name = "trainee")
  )
  private List<Employee> trainees;
}
