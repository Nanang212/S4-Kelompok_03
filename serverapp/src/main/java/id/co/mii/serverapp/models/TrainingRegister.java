package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_register")
public class TrainingRegister extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "training")
  private Training training;
  @ManyToOne
  @JoinColumn(name = "trainee")
  private Employee trainee;
  @ManyToOne
  @JoinColumn(name = "current_status")
  private Status currentStatus;
  @Lob
  @Column(columnDefinition = "LONGBLOB")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private byte[] attachment;
  @OneToMany(mappedBy = "trainingRegister", cascade = CascadeType.ALL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<History> histories;
  @OneToOne
  @JoinColumn(name = "survey_id")
  private Survey survey;
}
