package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training")
public class Training extends BaseEntity {
  private String title;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
  private Date startDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Jakarta")
  private Date endDate;
  private Integer quota;
  private Integer availSeat;
  private Integer duration;
  private String address;
  private String platformUrl;
  @Column(columnDefinition = "TEXT")
  private String description;
  private Boolean isOnline;
  @ManyToOne
  @JoinColumn(name = "category")
  private Category category;
  @ManyToOne
  @JoinColumn(name = "trainer")
  private Employee trainer;
  @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<TrainingRegister> trainingRegisters;
}
