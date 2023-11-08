package id.co.mii.clientapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRegisterRequest {
  private Integer trainingId;
  private Integer traineeId;
  private Integer statusId;
  private String notes;
}
