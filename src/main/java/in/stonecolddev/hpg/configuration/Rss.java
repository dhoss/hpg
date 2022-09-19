package in.stonecolddev.hpg.configuration;

import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rss {
  @Bean
  public RssReader rssReader() {
    return new RssReader();
  }
}