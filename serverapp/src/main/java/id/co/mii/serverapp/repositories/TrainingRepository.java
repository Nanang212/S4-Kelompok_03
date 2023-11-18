package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends BaseRepository<Training, Integer> {
  List<Training> findAllByCategoryId(Integer categoryId);
}
