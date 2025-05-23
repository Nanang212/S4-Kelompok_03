package id.co.mii.clientapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
  private Integer id;
  private String title;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
  private Date startDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
  private Date endDate;
  private Integer quota;
  private Integer availSeat;
  private String description;
  private String address;
  private String platformUrl;
  private Boolean isOnline;
  private Integer duration;
  private Employee trainer;
  private Category category;
}
