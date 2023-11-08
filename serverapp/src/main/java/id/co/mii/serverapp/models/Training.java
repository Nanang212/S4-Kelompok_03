package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import id.co.mii.serverapp.services.TrainingListener;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
  private String description;
  private Boolean isOnline;
  @ManyToOne
  @JoinColumn(name = "trainer")
  private Employee trainer;
  @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<TrainingRegister> trainingRegisters;
}
