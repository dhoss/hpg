package in.stonecolddev.hpg.feed;

import in.stonecolddev.hpg.configuration.FeedSource;

import java.io.IOException;
import java.util.Map;

public interface FeedCache {

  FeedLoaderRegistry feedLoaderRegistry();

  Map<FeedSource, Feed> list();

  default Feed loadFeed(FeedSource feedSource) throws IOException {
    return feedLoaderRegistry().load(feedSource.type())
                               .loadFeed(feedSource); // TODO: I think we want an additional call here to a persist() method or something
  }
}