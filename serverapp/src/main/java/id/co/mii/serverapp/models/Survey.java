package id.co.mii.serverapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.co.mii.serverapp.models.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Survey")
public class Survey extends BaseEntity {
    private Integer resource;
    @Column(name = "trainer_compotence")
    private Integer trainerCompotence;
    @Column(name = "learning_quality")
    private Integer learningQuality;
    @OneToOne
    @JoinColumn(name = "training_register_id", referencedColumnName = "id")
    private TrainingRegister trainingRegister;
}
