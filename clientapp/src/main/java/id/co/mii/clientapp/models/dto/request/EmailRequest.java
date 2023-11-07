package id.co.mii.clientapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmailRequest {

  private String to;
  private String subject;
  private String text;
  private String attachment;
  private String token;
}
