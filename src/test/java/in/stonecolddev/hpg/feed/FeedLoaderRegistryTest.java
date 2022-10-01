package in.stonecolddev.hpg.feed;

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
    FeedLoader feedLoader = (FeedSource) -> FeedBuilder.builder()
                                .name("test feed")
                                .items(List.of())
                                .build();
    var feedLoaderRegistry =
      new FeedLoaderRegistry(Map.of("LambdaFeedLoader", feedLoader));

    assertEquals(feedLoader, feedLoaderRegistry.load("Lambda"));
  }
}