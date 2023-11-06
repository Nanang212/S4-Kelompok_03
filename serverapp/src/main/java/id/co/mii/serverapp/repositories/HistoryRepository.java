package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.History;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends BaseRepository<History, Integer> {
}
