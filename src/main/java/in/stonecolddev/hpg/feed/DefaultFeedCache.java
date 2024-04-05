package in.stonecolddev.hpg.feed;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import in.stonecolddev.hpg.configuration.FeedSource;
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

  private final FeedLoaderRegistry feedLoaderRegistry;

  public DefaultFeedCache(FeedLoaderRegistry feedLoaderRegistry, Feeds feedConfiguration) {
    this.feedLoaderRegistry = feedLoaderRegistry;
    this.feedConfiguration = feedConfiguration;
    log.info("Populating feed cache");
    // TODO: caching should load from the db
    //       a scheduled job should refresh the db at an interval so HTTP calls are minimal
    log.debug("**** CACHE EXPIRE {}", feedConfiguration.cacheExpire());
    this.cache = Caffeine.newBuilder()
                         .expireAfterWrite(feedConfiguration.cacheExpire())
                         .build(this::loadFeed);
  }

  public FeedLoaderRegistry feedLoaderRegistry() {
    return this.feedLoaderRegistry;
  }

  public Map<FeedSource, Feed> list() {
    // TODO: limit/sort by, default to sort by date desc
    //        add configuration to allow for filtering anything older than a certain date
    return cache.getAll(feedConfiguration.feedSources());
  }
}