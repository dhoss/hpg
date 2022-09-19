package in.stonecolddev.hpg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Time {
  @Bean
  public Clock systemClock() {
    return Clock.systemUTC();
  }
}