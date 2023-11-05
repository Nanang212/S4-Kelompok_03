package id.co.mii.serverapp.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRegisterRequest {
  private Integer trainingId;
  private Integer traineeId;
  private String status;
  private String attachment;
}
