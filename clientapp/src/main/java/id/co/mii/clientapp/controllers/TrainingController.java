package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.Training;
import id.co.mii.clientapp.models.dto.request.TrainingRegisterRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.TrainingRegisterService;
import id.co.mii.clientapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@RequestMapping("/training")
public class TrainingController {
  private TrainingService trainingService;
  private EmployeeService employeeService;
  private TrainingRegisterService trainingRegisterService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("trainings", trainingService.getAll());
    return "training/index";
  }
  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model) {
    Training training = trainingService.getById(id);
    Employee loggedInEmployee = employeeService.getLoggedInUser();
    TrainingRegisterRequest trainingRegisterRequest = new TrainingRegisterRequest();
    trainingRegisterRequest.setTrainingId(training.getId());
    trainingRegisterRequest.setTraineeId(loggedInEmployee.getId());
    trainingRegisterRequest.setStatusId(2);

    model.addAttribute("training", training);
    model.addAttribute("loggedInEmp", loggedInEmployee);
    model.addAttribute("trainingRegisterRequest", trainingRegisterRequest);
      // model.addAttribute("isActive", "role");
    return "training/detail";
  }

  @PostMapping("/register")
  public String registTraining(@ModelAttribute TrainingRegisterRequest trainingRegisterRequest,
                               @RequestParam(name = "attachment") MultipartFile attachment) {
    trainingRegisterService.create(trainingRegisterRequest, attachment);
    return "redirect:/training/" + trainingRegisterRequest.getTrainingId();
  }

  @GetMapping("/register")
  public String getAllRegisterTraining(Model model) {
    return "training/register/index";
  }

  @GetMapping("/attend")
  public String getAttendedTraining(Model model) {
    Employee loggedInEmp = employeeService.getLoggedInUser();
    model.addAttribute("trainings", trainingService.getAllByTrainee(loggedInEmp.getUser().getUsername()));
    return "training/attend";
  }
}
