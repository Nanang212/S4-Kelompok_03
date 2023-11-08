package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import java.util.HashMap;
import java.util.Map;

// TODO
public class TrainingListener {

  @PostPersist
  public void sendEmailWhenTrainingCreated(Training training) {
//    employeeService.getAllByRoleId(3).forEach(employee -> {
//      EmailRequest emailRequest = new EmailRequest();
//      Map<String, Object> properties = new HashMap<>();
//      properties.put("title", training.getTitle());
//      properties.put("startDate", training.getStartDate());
//      properties.put("endDate", training.getEndDate());
//      properties.put("quota", training.getQuota());
//      properties.put("link", "http://localhost:9090/training/" + training.getId());
//      emailRequest.setTo(employee.getEmail());
//      emailRequest.setSubject("New Training Appear");
//      emailRequest.setBody("test.html");
//      emailRequest.setProperties(properties);
//      emailService.sendHtmlMessage(emailRequest);
//    });
  }
}
