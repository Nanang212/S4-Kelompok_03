package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
  private TrainingRegister trainingRegister;
  private Status status;
  private String notes;
  private Date createdAt;
  private Date updatedAt;
}
