package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
    private Integer id;
    private Integer resource;
    private Integer trainerCompotence;
    private Integer learningQuality;
    private TrainingRegister trainingRegister;
}
