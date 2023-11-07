package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
// TODO : Ubah relasi training - trainee menjadi many to many tipe ke 3
@EqualsAndHashCode(callSuper = true)
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
  private Integer duration;
  private String address;
  private String platformUrl;
  private Boolean isOnline;
  @ManyToOne
  @JoinColumn(name = "trainer")
  private Employee trainer;
  @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<TrainingRegister> trainingRegisters;
}
