package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
  Boolean existsByEmail(String email);
  Boolean existsByPhone(String phone);
  Optional<Employee> findByUserUsername(String username);
}
