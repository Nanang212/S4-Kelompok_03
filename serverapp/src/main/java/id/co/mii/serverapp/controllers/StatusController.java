package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/status")
public class StatusController {
  private StatusService statusService;

  @GetMapping
  public ResponseEntity<List<Status>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(statusService.getAll());
  }
}
