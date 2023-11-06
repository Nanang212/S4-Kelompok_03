package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends BaseRepository<Status, Integer> {
}
