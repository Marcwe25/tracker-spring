package marcWeiss.sTracker.component.resourcesNaming.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class RemoveCheckSumServletFilter
 */
@Component
public class RemoveCheckSumServletFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public RemoveCheckSumServletFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// pass the request along the filter chain

		if (request instanceof HttpServletRequest) {
			String requestURI = ((HttpServletRequest) request).getRequestURI();
			if (requestURI.contains("$checksum$")) {
				String contextPath = ((HttpServletRequest) request).getContextPath();
				String newURI = requestURI.replaceAll("\\$checksum\\$\\d*\\$", "").replaceAll("^\\" + contextPath, "");
				request.getRequestDispatcher(newURI).forward(request, response);
			} else {
				chain.doFilter(request, response);

			}

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
