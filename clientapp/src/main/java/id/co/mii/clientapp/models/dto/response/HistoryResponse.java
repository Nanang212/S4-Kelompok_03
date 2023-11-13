package id.co.mii.clientapp.models.dto.response;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.Training;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
  private Training training;
  private Employee trainee;
  List<Map<String, Object>> histories;
}
