package marcWeiss.sTracker.component.resourcesNaming.filters;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import marcWeiss.sTracker.component.resourcesNaming.HtmlResponseWrapper;
import marcWeiss.sTracker.component.resourcesNaming.LinkToRessourceUpdater;

@Component
public class AddCheckSumServletFilter implements Filter {

	LinkToRessourceUpdater linkToRessourceFinder;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
		this.linkToRessourceFinder = ctx.getBean(LinkToRessourceUpdater.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		//wrapping response to catch it content
		HtmlResponseWrapper responseWrapper = new HtmlResponseWrapper((HttpServletResponse) response);

		filterChain.doFilter(request, responseWrapper);

		String contentType = response.getContentType();
		if (contentType != null) {
			if(!contentType.contains("image")){
			String content = responseWrapper.getCaptureAsString();
			String replacedContent = linkToRessourceFinder.updateLinks(content);
			
			response.setContentLengthLong(replacedContent.getBytes(responseWrapper.getCharacterEncoding()).length);
			response.getWriter().write(replacedContent);}
			else{
				
				byte[] captureAsBytes = responseWrapper.getCaptureAsBytes();
				Integer contentLength = captureAsBytes.length;
				
				response.setContentType(contentType);
			    response.setContentLength(contentLength);
			    OutputStream out = response.getOutputStream();
			    out.write(captureAsBytes);
			    out.close();
			}
		}
	}

	@Override
	public void destroy() {

	}

}
