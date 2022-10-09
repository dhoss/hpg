package in.stonecolddev.hpg.feed;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.SqlParameterSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.stringtemplate.v4.ST;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Repository
public class DefaultFeedRepository implements FeedRepository {

  private final Logger log = LoggerFactory.getLogger(DefaultFeedRepository.class);

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private final RowMapper<Feed> feedRowMapper;

  private final SqlParameterSourceFactory<Feed> queryParams;

  public DefaultFeedRepository(
    RowMapper<Feed> feedRowMapper,
    SqlParameterSourceFactory<Feed> queryParams,
    NamedParameterJdbcTemplate jdbcTemplate) {

    this.feedRowMapper = feedRowMapper;
    this.queryParams = queryParams;
    this.jdbcTemplate = jdbcTemplate;
  }

  // TODO: upsert
  public Feed save(Feed feed) throws SQLException {
    log.info("Saving feed {}", feed.name());

    var keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
      """
      insert into feeds(name)
      values(:name)
      returning id
      """,
      queryParams.newSqlParameterSource(feed),
      keyHolder
    );

    var id = keyHolder.getKey().intValue();
    return find(id).orElseThrow(
      () -> new SQLException("Can't find feed with id: %d".formatted(id)));
  }

  public Optional<Feed> find(Integer id) {

    return jdbcTemplate.query(
      """
      select * from feeds
      where id = :id
      limit 1
      """,
      Map.of("id", id),
      feedRowMapper).stream().findFirst();
  }

  public Optional<Feed> find(String name) {
    return
      query(
        SelectBuilder.builder()
          .select("*")
          .from("feeds")
          .where("name = :name")
          .limit(1)
          .build(),
        Map.of("name", name),
        Feed.class
      );
  }

    private <T> Optional<T> query(Select select, Map<String, ?> sqlParameters, Class<T> type) {
    var selectTemplate = new ST(select.toString());
    return jdbcTemplate.query(
      selectTemplate.render(),
      sqlParameters,
      JdbcTemplateMapperFactory
        .newInstance()
        .addKeys("id")
        .newRowMapper(type)
    //  feedRowMapper
    ).stream().findFirst();
  }

}