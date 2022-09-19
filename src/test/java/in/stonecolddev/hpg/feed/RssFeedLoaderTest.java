package in.stonecolddev.hpg.feed;


import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import in.stonecolddev.hpg.configuration.FeedSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("unit-test")
@Tag("unit-test")
public class RssFeedLoaderTest {

  private final RssReader rssReader = mock(RssReader.class);

  private final Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));

  @Test
  public void loadFeed() throws IOException {
    var url = "https://stonecolddev.in/blog/url";
    var now = OffsetDateTime.now(clock);
    var feed = new Feed(
        "test feed",
        List.of(
            new FeedItem(
                0,
                "test title",
                URI.create(url),
                "test description",
                now,
                now
            )
        )
    );

    var feedItem = feed.items().get(0);

    // why do people still define classes like this?
    var readerItem = new Item();
    readerItem.setTitle(feedItem.title());
    readerItem.setDescription(feedItem.description());
    readerItem.setLink(feedItem.link().toString());
    readerItem.setPubDate(now.toString());


    when(rssReader.read(url))
        .thenReturn(Stream.of(readerItem));

    var rssFeedLoader = new RssFeedLoader(rssReader, clock);

    assertEquals(
        feed,
        rssFeedLoader.loadFeed(
            new FeedSource("test feed", URI.create(url), "rss")
        )
    );
  }
}