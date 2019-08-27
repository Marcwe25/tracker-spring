package marcWeiss.sTracker.statistic;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import marcWeiss.sTracker.component.helper.ParametersFinder;

@Configuration
@ComponentScans(value = { @ComponentScan(basePackages = "marcWeiss.sTracker.statistic.component"), })
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class StatisticConfig  extends JdbcConfiguration{

	@Autowired
	Environment environment;

	@Bean
	public DataSource mimiStatSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(environment.getProperty("DERBY_DRIVER"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl(environment.getProperty("DATABASE_LINK") + "/" + environment.getProperty("STAT_NAME"));
		dataSource.setUser(environment.getProperty("DATABASE_USER"));
		dataSource.setPassword(environment.getProperty("DATABASE_PASSWORD"));
		dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("POOL.INITIALPOOLSIZE")));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("POOL.MINPOOLSIZE")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		dataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		return dataSource;
	}

	@Bean
	public JdbcTemplate mimiJdbcTemplate(){
		return new JdbcTemplate(mimiStatSource());
	}
	
	@Bean
	public ParametersFinder reflectionUtility(){
		return new ParametersFinder();
	}

}
