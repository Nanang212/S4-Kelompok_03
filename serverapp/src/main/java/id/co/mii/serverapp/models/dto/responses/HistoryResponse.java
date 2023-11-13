package id.co.mii.serverapp.models.dto.responses;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.History;
import id.co.mii.serverapp.models.Training;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Training training;
    private Employee trainee;
    List<Map<String, Object>> histories;
}
