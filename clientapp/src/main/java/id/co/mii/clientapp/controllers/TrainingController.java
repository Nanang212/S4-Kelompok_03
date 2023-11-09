package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.Training;
import id.co.mii.clientapp.models.dto.request.TrainingRegisterRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.TrainingRegisterService;
import id.co.mii.clientapp.services.TrainingService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/training")
public class TrainingController {
  private TrainingService trainingService;
  private EmployeeService employeeService;
  private TrainingRegisterService trainingRegisterService;
  private AuthenticationSessionUtil authenticationSessionUtil;

  @GetMapping
  public String getAll(Model model) {
    List<String> authorities = authenticationSessionUtil
        .authentication()
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    model.addAttribute("authorities", authorities);
    return "training/index";
  }

  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model,
      @RequestParam(required = false) Map<String, Object> params) {
    Training training = trainingService.getById(id);
    Employee loggedInEmployee = employeeService.getLoggedInUser();
    TrainingRegisterRequest trainingRegisterRequest = new TrainingRegisterRequest();
    trainingRegisterRequest.setTrainingId(training.getId());
    trainingRegisterRequest.setTraineeId(loggedInEmployee.getId());
    trainingRegisterRequest.setStatusId(2);

    model.addAllAttributes(params);
    model.addAttribute("training", training);
    model.addAttribute("loggedInEmp", loggedInEmployee);
    model.addAttribute("trainingRegisterRequest", trainingRegisterRequest);
    // model.addAttribute("isActive", "role");
    return "training/detail";
  }

  @GetMapping("/register")
  public String getAllRegisterTraining(Model model) {
    List<String> authorities = authenticationSessionUtil
        .authentication()
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    model.addAttribute("authorities", authorities);
    return "training/register/index";
  }

  // TODO : Beri validasi apabila user sudah berpartisipasi
  @PostMapping("/register")
  public String registTraining(@ModelAttribute TrainingRegisterRequest trainingRegisterRequest,
      @RequestParam(name = "attachment") MultipartFile attachment) {
    try {
      trainingRegisterService.create(trainingRegisterRequest, attachment);
      return "redirect:/training/" + trainingRegisterRequest.getTrainingId();
    } catch (HttpClientErrorException exception) {
      return "redirect:/training/" + trainingRegisterRequest.getTrainingId() + "?error=true&message="
          + exception.getMessage();
    }
  }

  @PostMapping("/broadcast/{id}")
  public String broadcast(@PathVariable Integer id) {
    trainingService.broadcast(id);
    return "redirect:/training/" + id + "?message=Broadcast success";
  }

  @GetMapping("/attend")
  public String getAttendedTraining(Model model) {
    Employee loggedInEmp = employeeService.getLoggedInUser();
    model.addAttribute("trainings", trainingService.getAllByTrainee(loggedInEmp.getUser().getUsername()));
    return "training/attend";
  }

  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Integer id, Model model) {
    // model.addAttribute("loggedInEmployee", employeeService.getLoggedInUser());
    // model.addAttribute("id", id);
    Training training = trainingService.getById(id);
    model.addAttribute("training", training);
    return "training/update";
  }
}
