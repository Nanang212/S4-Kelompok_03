package id.co.mii.serverapp.services.base;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.base.BaseEntity;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import id.co.mii.serverapp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class BaseService<E extends BaseEntity, T> {
  @Autowired
  private BaseRepository<E, T> repository;

  public List<E> getAll() {
    return repository.findAll();
  }

  public E getById(T id) {
    return repository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity is not found"));
  }

  public E create(E entity) {
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());
    return repository.save(entity);
  }

  public E update(T id, E entity) {
    getById(id);
    entity.setId((Integer) id);
    return repository.save(entity);
  }

  public void delete(T id) {
    E entity = getById(id);
    repository.delete(entity);
  }
}
