package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.History;
import id.co.mii.serverapp.services.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/histories")
public class HistoryController {
  private HistoryService historyService;

  @GetMapping
  public ResponseEntity<List<History>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(historyService.getAll());
  }
}
