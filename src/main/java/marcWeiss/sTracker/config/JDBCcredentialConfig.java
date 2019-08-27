package marcWeiss.sTracker.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJdbcRepositories(
		basePackages={
				"marcWeiss.sTracker.entity.credentials",
				"marcWeiss.sTracker.repositories.credential"	
		}
		
		)
@EnableTransactionManagement
public class JDBCcredentialConfig extends JdbcConfiguration {
	
	@Autowired
	DataSource credentialSource;

	@Bean
	NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(credentialSource);
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(credentialSource);
	}
    

}
