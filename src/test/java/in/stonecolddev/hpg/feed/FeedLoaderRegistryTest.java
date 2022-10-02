package in.stonecolddev.hpg.feed;

import in.stonecolddev.hpg.configuration.FeedSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("unit-test")
@Tag("unit-test")
public class FeedLoaderRegistryTest {

  @Test
  public void load() {
    var feedLoader = new FeedLoader() {
      @Override
      public Feed load(FeedSource feedSource) {
        return FeedBuilder.builder().name("test feed").items(List.of()).build();
      }

      @Override
      public void save(Feed feed) {

      }
    };

    var feedLoaderRegistry =
      new FeedLoaderRegistry(Map.of("TestFeedLoader", feedLoader));

    assertEquals(feedLoader, feedLoaderRegistry.get("Test"));
  }
}