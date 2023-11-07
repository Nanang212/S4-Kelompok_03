package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRegister {
  private Integer id;
  private Training training;
  private Employee trainee;
  private Status currentStatus;
}
