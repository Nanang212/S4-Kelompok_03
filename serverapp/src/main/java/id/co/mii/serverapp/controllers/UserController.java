package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
  private UserService userService;

  @PostMapping("/{userId}/add-role/{roleId}")
  public ResponseEntity<User> addRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.addRole(userId, roleId));
  }
}
