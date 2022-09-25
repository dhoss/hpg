package in.stonecolddev.hpg.feed;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import in.stonecolddev.hpg.configuration.FeedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

// TODO: tests
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
    return new Feed(
        feedSource.name(),
        rssReader.read(feedSource.uri().toString()).map(
            i ->
              new FeedItem(
                  0,
                  i.getTitle().orElseGet(() -> "no title???"),
                  URI.create(i.getLink()
                              .orElseGet(() -> "https://stonecolddev.in")),
                  // TODO: enable option to detect URL
                  i.getDescription().orElseGet(() -> "no description"),
                  dateHandler(i),
                  OffsetDateTime.now(clock)
              )
        ).collect(Collectors.toList())
    );
  }

  // TODO: this date is breaking this: 2021-09-25T01:55:00.001+01:00
  private OffsetDateTime dateHandler(Item item) {
    try {
      return OffsetDateTime.parse(
          item.getPubDate()
              .orElseThrow(() -> new RuntimeException("No pubDate provided"))
      );
    } catch (java.time.format.DateTimeParseException | java.lang.IllegalArgumentException e) {
      log.error("Error handling datetime: {}", e.getMessage());
      return OffsetDateTime.now(clock);
    }
  }
}