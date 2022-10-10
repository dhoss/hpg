package in.stonecolddev.hpg.feed;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import in.stonecolddev.hpg.configuration.Feeds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

// TODO: tests
@Component
public class DefaultFeedCache implements FeedCache {

  private final Logger log = LoggerFactory.getLogger(DefaultFeedCache.class);

  private final LoadingCache<FeedSource, Feed> cache;

  private final Feeds feedConfiguration;

  private final FeedService feedService;

  private final FeedLoaderRegistry feedLoaderRegistry;

  public DefaultFeedCache(
    FeedLoaderRegistry feedLoaderRegistry,
    Feeds feedConfiguration,
    FeedService feedService) {

    this.feedLoaderRegistry = feedLoaderRegistry;
    this.feedConfiguration = feedConfiguration;
    this.feedService = feedService;

    log.info("Populating feed cache");

    this.cache = Caffeine.newBuilder()
                         .expireAfterAccess(feedConfiguration.cacheExpire())
                         .build(this::populate);
  }

  // TODO: try out virtual threads wherever populate ends up getting called: https://openjdk.org/jeps/425
  @Override
  public Feed populate(FeedSource feedSource) {
    // TODO: Future that returns feed data from database after it's populated from feed sources
    return feedService.find(feedSource.name())
             // TODO: don't die here unless we've actually hit a problem
             //       poll until feed loading has completed and then return
             .orElseThrow(
               () -> new IllegalArgumentException(
                 String.format("Can't find feed: %s", feedSource.name())));
  }

  public FeedLoaderRegistry feedLoaderRegistry() {
    return this.feedLoaderRegistry;
  }

  public Map<FeedSource, Feed> all() {
    // TODO: limit/sort by, default to sort by date desc
    //        add configuration to allow for filtering anything older than a certain date
    log.debug("***** FEED SOURCES {}", feedConfiguration.feedSources());
    return cache.getAll(feedConfiguration.feedSources());
  }
}