package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends BaseRepository<Training, Integer> {
}
