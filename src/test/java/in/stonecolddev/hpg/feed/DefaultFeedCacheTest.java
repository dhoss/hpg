package in.stonecolddev.hpg.feed;


import in.stonecolddev.hpg.configuration.FeedSource;
import in.stonecolddev.hpg.configuration.Feeds;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("unit-test")
@Tag("unit-test")
public class DefaultFeedCacheTest {

  private final Feeds feedConfiguration  = new Feeds(
      Duration.of(1, TimeUnit.SECONDS.toChronoUnit()),
      List.of(
          new FeedSource(
              "test feed", URI.create("https://stonecolddev.in"), "rss")
      )
  );

  private final FeedLoaderRegistry feedLoaderRegistry = mock(FeedLoaderRegistry.class);

  private final RssFeedLoader rssFeedLoader = mock(RssFeedLoader.class);


  @Test
  public void list() throws IOException {
     var feed = new Feed(
         "test feed",
         List.of(
             FeedItemBuilder.builder()
                            .id(1)
                 .title("test title")
                 .description("test description")
                 .link(URI.create("https://stonecolddev.in/blog/url"))
                 .indexed(OffsetDateTime.now())
                 .published(Optional.of(OffsetDateTime.now()))
                 .build()
         )
     );

     var feedSource = feedConfiguration.feedSources().get(0);

     when(feedLoaderRegistry.load("rss")).thenReturn(rssFeedLoader);
     when(rssFeedLoader.loadFeed(feedConfiguration.feedSources().get(0))).thenReturn(feed);

     var feedCache = new DefaultFeedCache(feedLoaderRegistry, feedConfiguration);
     assertEquals(Map.of(feedSource, feed), feedCache.list());
     assertEquals(feed, feedCache.loadFeed(feedSource));
  }

}