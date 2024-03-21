package in.stonecolddev.hpg.configuration;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "feeds")
@Profile({"local", "unit-test", "it-test", "dev", "prod"})
public record Feeds(
    Duration cacheExpire,
    List<FeedSource> feedSources) {}