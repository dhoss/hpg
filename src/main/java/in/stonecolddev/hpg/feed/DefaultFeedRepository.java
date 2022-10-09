package in.stonecolddev.hpg.feed;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultFeedRepository implements FeedRepository {

  private final NamedParameterJdbcDaoSupport dao;

  public DefaultFeedRepository(NamedParameterJdbcDaoSupport dao) {
    this.dao = dao;
  }

  public Feed save(Feed feed) {
    return feed;
  }
}
