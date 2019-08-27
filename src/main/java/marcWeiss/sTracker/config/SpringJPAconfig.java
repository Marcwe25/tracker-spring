package marcWeiss.sTracker.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories(
	    basePackages = {"marcWeiss.sTracker.repositories.jpa",
	    				"marcWeiss.sTracker.entity.jpa"}, 
	    entityManagerFactoryRef = "userEntityManager", 
	    transactionManagerRef = "userTransactionManager"
	)
@PropertySource("classpath:config.properties")
public class SpringJPAconfig {

	@Autowired
	private Environment environment;
	
	@Bean
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] {
        			"marcWeiss.sTracker.entity.jpa"
        									});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",environment.getProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", "true");
//        properties.put("logging.level.org.hibernate.type.descriptor.sql", "trace");
        em.setJpaPropertyMap(properties);
        return em;
    }
 
	@Bean
	public DataSource dataSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(environment.getProperty("DERBY_DRIVER"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl(
				environment.getProperty("DATABASE_LINK")
				+"/"
				+environment.getProperty("DATABASE_NAME"));
		dataSource.setUser(environment.getProperty("DATABASE_USER"));
		dataSource.setPassword(environment.getProperty("DATABASE_PASSWORD"));
		dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("POOL.INITIALPOOLSIZE")));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("POOL.MINPOOLSIZE")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		dataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		return dataSource;
	}
 
    @Primary
    @Bean
    public PlatformTransactionManager userTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return transactionManager;
    }
	
}
