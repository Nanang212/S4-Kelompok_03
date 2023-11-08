package id.co.mii.clientapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/history")
public class HistoryController {

  @GetMapping
  public String getAll(Model model) {
    return "history/index";
  }
}
