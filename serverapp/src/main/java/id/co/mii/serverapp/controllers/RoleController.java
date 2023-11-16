package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
  private RoleService roleService;

  @GetMapping
  public ResponseEntity<List<Role>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(roleService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Role> getById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(roleService.getById(id));
  }
}
