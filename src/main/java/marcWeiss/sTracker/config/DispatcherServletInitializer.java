package marcWeiss.sTracker.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import marcWeiss.sTracker.component.resourcesNaming.filters.AddCheckSumServletFilter;
import marcWeiss.sTracker.component.resourcesNaming.filters.RemoveCheckSumServletFilter;
import marcWeiss.sTracker.statistic.StatisticConfig;


public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{
							SpringMVCConfig.class
							,SpringJPAconfig.class
							,SecurityConfig.class
							,StatisticConfig.class
							,JDBCcredentialConfig.class
							};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{
				new AddCheckSumServletFilter()
				, new RemoveCheckSumServletFilter(),
				};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new RequestContextListener());
	}
	
	
}
