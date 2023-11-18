package id.co.mii.serverapp.utils;

import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
@AllArgsConstructor
public class ServerUtil {
  @Bean
  CommandLineRunner run(TrainingService trainingService) {
    return args -> {
      Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
      trainingService.getAll(2).forEach(training -> {
        if (training.getEndDate().before(currentDate) || training.getEndDate().equals(currentDate)) {
          TrainingRequest trainingRequest = new TrainingRequest();
          trainingRequest.setCategoryId(1);
          trainingService.update(training.getId(), trainingRequest);
        }
      });
      trainingService.getAll(3).forEach(training -> {
        if (training.getStartDate().before(currentDate) || training.getStartDate().equals(currentDate)) {
          TrainingRequest trainingRequest = new TrainingRequest();
          trainingRequest.setCategoryId(2);
          trainingService.update(training.getId(), trainingRequest);
        }
      });
    };
  }
}
