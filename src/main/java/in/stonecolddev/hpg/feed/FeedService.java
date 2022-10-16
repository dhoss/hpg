package in.stonecolddev.hpg.feed;

import in.stonecolddev.hpg.configuration.Feeds;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class FeedService {

  private final FeedRepository feedRepository;

  private final FeedJobScheduler feedJobScheduler;

  private final Feeds feeds;

  public FeedService(
    DefaultFeedRepository feedRepository,
    FeedJobScheduler feedJobScheduler,
    Feeds feeds
  ) {
    this.feedRepository = feedRepository;
    this.feedJobScheduler = feedJobScheduler;
    this.feeds = feeds;
  }

  public Feed save(Feed feed) throws SQLException {
    return feedRepository.save(feed);
  }

 // public Optional<Feed> find(String name) {
 //   return feedRepository.find(name)
 //            .or(() -> {
 //              feedJobScheduler.enqueue(new FeedJob(feeds.feedSourceByName(name)));
 //              return Optional.empty();
 //            });
 // }

  // TODO: pagination
  public List<Feed> all() throws SQLException, IOException {
    refresh();
    return feedRepository.all();
  }

  public void refresh() throws IOException, SQLException {
    for (var feedSource : feeds.feedSources()) {
      feedJobScheduler.enqueue(new FeedJob(feedSource));
    }
  }
}