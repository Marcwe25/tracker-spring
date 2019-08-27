package marcWeiss.sTracker.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.component.resourcesNaming.filters.AddCheckSumServletFilter;
@EnableWebMvc
@Configuration
@PropertySource(name="data", value="classpath:data.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScans(value = {
		@ComponentScan(basePackages="marcWeiss.sTracker.component"),
		@ComponentScan(basePackages = "marcWeiss.sTracker.core.securityConfig"),
		@ComponentScan(basePackages = "marcWeiss.sTracker.core.helper"),

})
public class SpringMVCConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}
	
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
        		.addResourceLocations("/resources/")
        		.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
    
    @Bean
    public Validator validatorFactory (MessageSource messageSource) {
        LocalValidatorFactoryBean validator =  new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
    
    @Bean
    public ResourceBundleMessageSource messageSource(){
    	ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
    	rs.addBasenames("resourceBundles/messages");
    	return rs;
    }
    
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.TARGET_CLASS)
    public  PageProperties userPageProperties(){
    	System.out.println("constructing PageProperties");
    	PageProperties properties = new PageProperties();
    	properties.setFrom(LocalDate.now());
    	properties.setTo(LocalDate.now());
    	properties.setPagingValue(0);
    	System.out.println("returning PageProperties: " + properties);
		return properties;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.TARGET_CLASS)
    public  PageProperties adminStatpage(){
    	PageProperties properties = new PageProperties();
    	properties.setCurrentList("users");
    	properties.setTo(LocalDate.now());
    	properties.setFrom(LocalDate.ofYearDay(2017, 1));
    	properties.setPagingValue(0);
    	return properties;
    }
    
    @Bean
    public AddCheckSumServletFilter assetFilter(){
    	return new AddCheckSumServletFilter();
    }
    
    @Bean
    public ThemeSource themeSource() {
       ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
       themeSource.setBasenamePrefix("theme/");
       return themeSource;
    }

    @Bean
    public ThemeResolver themeResolver() {
       CookieThemeResolver resolver = new CookieThemeResolver();
       resolver.setDefaultThemeName("compact");
       return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
       themeChangeInterceptor.setParamName("theme");
       registry.addInterceptor(themeChangeInterceptor);
       registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.forLanguageTag("he"));
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
		WebMvcConfigurer.super.configureMessageConverters(converters);
	}
    
}


