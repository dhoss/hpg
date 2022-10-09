package in.stonecolddev.hpg.feed;


import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class FeedService {

  private final FeedRepository feedRepository;

  public FeedService(DefaultFeedRepository feedRepository) {
    this.feedRepository = feedRepository;
  }

  public Feed save(Feed feed) throws SQLException {
    return feedRepository.save(feed);
  }

  public Optional<Feed> find(String name) {
    return feedRepository.find(name);
  }

}