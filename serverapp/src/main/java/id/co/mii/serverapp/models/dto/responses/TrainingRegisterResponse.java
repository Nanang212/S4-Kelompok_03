package id.co.mii.serverapp.models.dto.responses;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.models.Training;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRegisterResponse {
  private Training training;
  private Employee trainer;
  private List<Map<String, Object>> traineeStatus;
}
