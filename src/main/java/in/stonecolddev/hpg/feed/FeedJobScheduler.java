package in.stonecolddev.hpg.feed;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface FeedJobScheduler {

  CompletableFuture<Feed> enqueue(FeedJob feedJob) throws SQLException, IOException;
}