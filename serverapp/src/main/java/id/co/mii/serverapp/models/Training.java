package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training")
public class Training extends BaseEntity {
  private String title;
  private Date startDate;
  private Date endDate;
  private Integer quota;
  private String address;
  private String platformUrl;
  private Boolean isOnline;
  @ManyToOne
  @JoinColumn(name = "trainer")
  private Employee trainer;
}
