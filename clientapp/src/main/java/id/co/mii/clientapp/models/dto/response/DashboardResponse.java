package id.co.mii.clientapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
  private Integer training;
  private Integer trainer;
  private Integer trainee;
}
