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
import java.util.Optional;
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

  private final OffsetDateTime now = OffsetDateTime.now(clock);

  private final String url = "https://stonecolddev.in/blog/url";

  private final Item readerItem = new Item();

  private final FeedItem feedItem = FeedItemBuilder.builder()
                                   .id(0)
                                   .title("test title")
                                   .link(URI.create(url))
                                   .description("test description")
                                   .indexed(now)
                                   .published(Optional.of(now))
                                   .build();
  private final Feed feed = FeedBuilder.builder()
                              .name("test feed")
                              .items(List.of(feedItem))
                              .build();


  private final FeedSource feedSource = new FeedSource("test feed", URI.create(url), "rss");

  // TODO: fix rss tests
  @Test
  public void loadFeed() throws IOException {

    // why do people still define classes like this?
    readerItem.setTitle(feedItem.title());
    readerItem.setDescription(feedItem.description());
    readerItem.setLink(feedItem.link().toString());
    readerItem.setPubDate(now.toString());

    when(rssReader.read(url)).thenReturn(Stream.of(readerItem));

    var rssFeedLoader = new RssFeedLoader(rssReader, clock);

    assertEquals(feed, rssFeedLoader.loadFeed(feedSource));
  }

  @Test
  public void dateTimeParsingExceptions() throws IOException {

    readerItem.setTitle(feedItem.title());
    readerItem.setDescription(feedItem.description());
    readerItem.setLink(feedItem.link().toString());
    readerItem.setPubDate("Sat, 1 Oct 2022 19:00:51 +0000");

    when(rssReader.read(url)).thenReturn(Stream.of(readerItem));

    var rssFeedLoader = new RssFeedLoader(rssReader, clock);
    assertEquals(
      feed.withItems(
        List.of(
          feedItem.withPublished(
            Optional.empty()))),
      rssFeedLoader.loadFeed(feedSource));
  }
}