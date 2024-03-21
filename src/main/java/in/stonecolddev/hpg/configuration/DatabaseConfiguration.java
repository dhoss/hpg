package in.stonecolddev.hpg.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile({"local", "local-docker", "unit-test", "it-test", "dev", "prod"})
public class DatabaseConfiguration {

  @Bean
  public NamedParameterJdbcOperations operations() {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport() {
    NamedParameterJdbcDaoSupport support = new NamedParameterJdbcDaoSupport();
    support.setDataSource(dataSource());

    return support;
  }

  @Bean
  @ConfigurationProperties("spring.datasource.hikari")
  public HikariDataSource dataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
}