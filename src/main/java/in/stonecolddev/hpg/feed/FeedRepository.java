package in.stonecolddev.hpg.feed;

import org.springframework.stereotype.Repository;

public interface FeedRepository {

  Feed save(Feed feed);
}