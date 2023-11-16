package id.co.mii.serverapp.utils;

import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class SchedulerConfig implements SchedulingConfigurer {
  private TrainingService trainingService;

  @Bean
  public Executor taskExecutor() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskExecutor());
    taskRegistrar.addTriggerTask(
            new Runnable() {
              @Override
              public void run() {
                log.info("schedule active");
              }
            },
            new Trigger() {
              @Override
              public Date nextExecutionTime(TriggerContext context) {
                Optional<Date> lastCompletionTime =
                        Optional.ofNullable(context.lastCompletionTime());
                Instant nextExecutionTime =
                        lastCompletionTime.orElseGet(Date::new).toInstant()
                                .plusMillis(10000);
                return Date.from(nextExecutionTime);
              }
            }
    );
  }
}
