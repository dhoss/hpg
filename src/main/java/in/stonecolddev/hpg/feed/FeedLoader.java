package in.stonecolddev.hpg.feed;

import java.io.IOException;
import java.sql.SQLException;

// TODO: implement a file loader so you can upload a feed export and have it ingest everything
public interface FeedLoader {

  Feed retrieve(FeedSource feedSource) throws IOException;

  Feed save(Feed feed) throws SQLException;

  default Feed load(FeedSource feedSource) throws IOException, SQLException {
    return save(retrieve(feedSource));
  }

}