package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.History;
import id.co.mii.clientapp.services.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/histories")
public class RestHistoryController {
  private HistoryService historyService;

  @GetMapping
  public List<History> getAll() {
    return historyService.getAll();
  }
}
