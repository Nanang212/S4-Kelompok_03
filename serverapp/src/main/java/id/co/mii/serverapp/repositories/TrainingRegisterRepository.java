package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.TrainingRegister;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingRegisterRepository extends BaseRepository<TrainingRegister, Integer> {
  Optional<TrainingRegister> findByTrainingAndTrainee(Training training, Employee trainee);
}
