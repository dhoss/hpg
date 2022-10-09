package in.stonecolddev.hpg.feed;

import java.io.IOException;

// TODO: implement a file loader so you can upload a feed export and have it ingest everything
public interface FeedLoader {

  Feed retrieve(FeedSource feedSource) throws IOException;

  Feed save(Feed feed);

  default Feed load(FeedSource feedSource) throws IOException {
    return save(retrieve(feedSource));
  }

}