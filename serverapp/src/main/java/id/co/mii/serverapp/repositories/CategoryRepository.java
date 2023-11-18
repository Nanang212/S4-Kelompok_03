package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Category;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {
}
