package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Status;
import id.co.mii.clientapp.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/status")
public class RestStatusController {
  private StatusService statusService;

  @GetMapping
  public List<Status> getAll() {
    return statusService.getAll();
  }
}
