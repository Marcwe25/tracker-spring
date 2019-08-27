package marcWeiss.sTracker.config;

import java.beans.PropertyVetoException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import marcWeiss.sTracker.component.handler.CustomeAccessDeniedHandler;
import marcWeiss.sTracker.component.handler.MySimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:config.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment environment;
	
	@Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(credentialSource());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
        
		http
		.authorizeRequests()
		.antMatchers("/","/index.jsp").permitAll()
		.antMatchers("/login*","/register/**","/home","/resources/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(myAuthenticationSuccessHandler())
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.and()
		.rememberMe().key("mykey")
		.and()
		.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler())
		;
	}
	
	@Bean
	public DataSource credentialSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(environment.getProperty("DERBY_DRIVER"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl(
				environment.getProperty("DATABASE_LINK")
				+"/"
				+environment.getProperty("DATABASE_CREDENTIAL"));
		dataSource.setUser(environment.getProperty("DATABASE_USER"));
		dataSource.setPassword(environment.getProperty("DATABASE_PASSWORD"));
		dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("POOL.INITIALPOOLSIZE")));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("POOL.MINPOOLSIZE")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		dataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("POOL.MAXPOOLSIZE")));
		return dataSource;
	}

	@Bean
	public PasswordEncoder bcryptEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomeAccessDeniedHandler();
	}
	
}
