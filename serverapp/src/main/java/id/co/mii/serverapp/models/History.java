package id.co.mii.serverapp.models;

import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "training_register")
  private TrainingRegister trainingRegister;
  @ManyToOne
  @JoinColumn(name = "status")
  private Status status;
  private String notes;
}
