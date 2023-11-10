package id.co.mii.serverapp.repositories;

import org.springframework.stereotype.Repository;
import id.co.mii.serverapp.models.Survey;
import id.co.mii.serverapp.repositories.base.BaseRepository;

@Repository
public interface SurveyRepository extends BaseRepository<Survey, Integer> {

}
