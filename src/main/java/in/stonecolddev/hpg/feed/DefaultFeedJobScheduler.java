package in.stonecolddev.hpg.feed;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Component
public class DefaultFeedJobScheduler implements FeedJobScheduler {

  private final FeedLoaderRegistry feedLoaderRegistry;

  public DefaultFeedJobScheduler(FeedLoaderRegistry feedLoaderRegistry) {
    this.feedLoaderRegistry = feedLoaderRegistry;
  }

  @Async
  public CompletableFuture<Feed> enqueue(FeedJob feedJob) throws
    SQLException,
      IOException {
    return CompletableFuture.completedFuture(
      feedLoaderRegistry.get(feedJob.feedSource().type()).load(feedJob.feedSource()));
  }
}