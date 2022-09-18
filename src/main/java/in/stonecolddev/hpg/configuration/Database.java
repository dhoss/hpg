package in.stonecolddev.hpg.configuration;

import com.zaxxer.hikari.HikariDataSource;
import in.stonecolddev.hpg.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile({"local", "unit-test", "it-test", "dev", "prod"})
@EnableJdbcRepositories(basePackageClasses = { Repository.class })
public class Database  {

    private final Environment env;

    @Autowired
    public Database(Environment env) {
        this.env = env;
    }

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