package id.co.mii.serverapp.models;

import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  private String status;
  private String attachment;
}
