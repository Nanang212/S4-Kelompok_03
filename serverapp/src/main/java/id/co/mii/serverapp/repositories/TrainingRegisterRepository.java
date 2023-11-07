package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.TrainingRegister;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRegisterRepository extends BaseRepository<TrainingRegister, Integer> {
}
