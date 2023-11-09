package id.co.mii.serverapp.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
  private Integer training;
  private Integer trainee;
  private Integer trainer;
}
