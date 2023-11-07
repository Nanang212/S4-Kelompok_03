package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.repositories.UserRepository;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService extends BaseService<User, Integer> {
  private UserRepository userRepository;
  private RoleService roleService;

  public User addRole(Integer userId, Integer roleId) {
    User user = getById(userId);
    Set<Role> roles = user.getRoles();
    roles.add(roleService.getById(roleId));
    user.setRoles(roles);
    return userRepository.save(user);
  }
}
