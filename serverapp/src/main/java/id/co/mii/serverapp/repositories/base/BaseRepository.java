package id.co.mii.serverapp.repositories.base;

import id.co.mii.serverapp.models.base.BaseEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface BaseRepository<E extends BaseEntity, T> extends JpaRepository<E, T> {
}
