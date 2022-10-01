package in.stonecolddev.hpg.feed;

import com.apptasticsoftware.rssreader.RssReader;
import in.stonecolddev.hpg.configuration.FeedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RssFeedLoader implements FeedLoader {

  private final Logger log = LoggerFactory.getLogger(RssFeedLoader.class);

  private final Clock clock;

  private final RssReader rssReader;

  public RssFeedLoader(
    RssReader rssReader,
    Clock clock
  ) {
    this.rssReader = rssReader;
    this.clock = clock;
  }

  // TODO: ideally this will be enqueued and then called by a job executor
  public Feed loadFeed(FeedSource feedSource) throws IOException {
    log.info("Loading feed {}", feedSource.name());
    return FeedBuilder.builder()
             .name(feedSource.name())
             .items(
               rssReader.read(feedSource.uri().toString())
                 .map(
                   i ->
                     FeedItemBuilder.builder()
                       .title(i.getTitle().orElseThrow(
                         () -> new IllegalArgumentException("No title provided")))
                       .link(URI.create(i.getLink().orElseThrow(
                         () -> new IllegalArgumentException("No link provided"))))
                       /* TODO: enable option to detect URL */
                       .description(
                         i.getDescription().orElse("(no description provided)"))
                       .indexed(OffsetDateTime.now(clock))
                       .published(safeDateTime(i.getPubDate()))
                       .build())
                 .collect(Collectors.toList()))
             .build();
  }

  private Optional<OffsetDateTime> safeDateTime(Optional<String> dateTimeString) {
    try {
      return dateTimeString.map(OffsetDateTime::parse);
    } catch (java.time.format.DateTimeParseException e) {
      log.error("Unable to parse String to ZoneDateTime: {}", e.getMessage());
      return Optional.empty();
    }
  }
}