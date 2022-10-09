package in.stonecolddev.hpg.feed;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface FeedCache {

  FeedLoaderRegistry feedLoaderRegistry();

  Map<FeedSource, Feed> all();

  default Feed populate(FeedSource feedSource) throws IOException, SQLException {
    return feedLoaderRegistry().get(feedSource.type())
                               .load(feedSource); // TODO: I think we want an additional call here to a persist() method or something
  }
}