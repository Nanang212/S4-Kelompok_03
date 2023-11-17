package id.co.mii.serverapp.utils;

import id.co.mii.serverapp.models.Category;
import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.services.CategoryService;
import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@Slf4j
@EnableAsync
public class SchedulerConfig implements SchedulingConfigurer {
  @Autowired
  private TrainingService trainingService;
  @Autowired
  private CategoryService categoryService;
  private ScheduledTaskRegistrar taskRegistrar;

  @Bean
  public Executor taskExecutor() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Async
  @Scheduled(fixedRate = 60000)
  public void doTask() {
    log.info("Reloading task for scheduling");
    configureTasks(taskRegistrar);
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    this.taskRegistrar = taskRegistrar;
    taskRegistrar.setScheduler(taskExecutor());
    trainingService.getAll(2).forEach(training -> {
      taskRegistrar.addTriggerTask(
              new Runnable() {
                @Override
                public void run() {
                  TrainingRequest trainingRequest = new TrainingRequest();
                  trainingRequest.setCategoryId(1);
                  log.info("Updated to done");
                  trainingService.update(training.getId(), trainingRequest);
                }
              },
              new CronTrigger(getCronExpression(training.getEndDate()))
      );
    });
    trainingService.getAll(3).forEach(training -> {
      taskRegistrar.addTriggerTask(
              new Runnable() {
                @Override
                public void run() {
                  TrainingRequest trainingRequest = new TrainingRequest();
                  trainingRequest.setCategoryId(2);
                  log.info("Updated to ongoing");
                  trainingService.update(training.getId(), trainingRequest);
                }
              },
              new CronTrigger(getCronExpression(training.getStartDate()))
      );
    });
  }

  private String getCronExpression(Date date) {
    // Convert the endDate to a cron expression, adjust as needed
    // Here, we use SimpleDateFormat to get the components of the date
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    int second = cal.get(Calendar.SECOND);
    int minute = cal.get(Calendar.MINUTE);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH) + 1; // Note: Cron months are 1-based
    int year = cal.get(Calendar.YEAR);

    // Create a cron expression with the extracted components
    return String.format("%d %d %d %d %d ?", second, minute, hour, dayOfMonth, month);
  }
}
