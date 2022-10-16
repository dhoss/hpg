package in.stonecolddev.hpg.configuration;

import in.stonecolddev.hpg.feed.FeedSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "feeds")
@ConstructorBinding
@Profile({"local", "unit-test", "it-test", "dev", "prod"})
public record Feeds(Duration cacheExpire, List<FeedSource> feedSources) {

  public FeedSource feedSourceByName(String name) {
    return feedSources.stream()
             .filter(fs -> fs.name().equalsIgnoreCase(name))
             .findFirst()
             .orElseThrow(
               () -> new IllegalArgumentException("Can't find feed source: %s".formatted(name)));
  }
}