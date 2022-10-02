package in.stonecolddev.hpg.feed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeedLoaderRegistry {
  private final Logger log = LoggerFactory.getLogger(FeedLoaderRegistry.class);

  private final Map<String, FeedLoader> feedLoaders;

  public FeedLoaderRegistry(Map<String, FeedLoader> feedLoaders) {
    log.info("Loading feedloaders: {}", feedLoaders.keySet());
    this.feedLoaders = feedLoaders;
  }

  public FeedLoader load(String feedLoaderName) {
    log.info("Loading feed handler {}", feedLoaderName);
    return feedLoaders.get("%sFeedLoader".formatted(feedLoaderName));
  }
}