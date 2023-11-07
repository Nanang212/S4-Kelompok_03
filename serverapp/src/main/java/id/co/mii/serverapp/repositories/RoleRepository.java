package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {
}
