package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Role;
import id.co.mii.clientapp.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/role")
public class RestRoleController {
  private RoleService roleService;

  @GetMapping
  public List<Role> getAll() {
    return roleService.getAll();
  }

  @GetMapping("/{id}")
  public Role getById(@PathVariable Integer id) {
    return roleService.getById(id);
  }

  @PostMapping
  public Role create(@RequestBody Role region) {
    return roleService.create(region);
  }

  @PutMapping("/{id}")
  public Role update(@PathVariable Integer id, @RequestBody Role region) {
    return roleService.update(id, region);
  }

  @DeleteMapping("/{id}")
  public Role delete(@PathVariable Integer id) {
    return roleService.delete(id);
  }
}
