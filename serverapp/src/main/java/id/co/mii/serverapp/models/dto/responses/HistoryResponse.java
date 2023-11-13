package id.co.mii.serverapp.models.dto.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private String status;
    private Date date;
}
