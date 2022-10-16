package in.stonecolddev.hpg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class Executor {

  @Bean(name="taskExecutor")
  public ThreadPoolTaskExecutor taskExecutor() {
    final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("FeedThread-");
    executor.initialize();
    return executor;
  }
}