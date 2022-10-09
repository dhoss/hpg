package in.stonecolddev.hpg.feed;

import java.sql.SQLException;
import java.util.Optional;

public interface FeedRepository {

  Feed save(Feed feed) throws SQLException;

  Optional<Feed> find(Integer id);

  Optional<Feed> find(String name);
}