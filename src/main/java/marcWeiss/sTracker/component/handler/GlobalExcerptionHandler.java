package marcWeiss.sTracker.component.handler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import marcWeiss.sTracker.statistic.component.service.StatisticServiceType;

@ControllerAdvice
public class GlobalExcerptionHandler {

	@Autowired
	private  HttpServletRequest request;
	@Autowired
	ServletContext servletContext;
	@Autowired
	StatisticServiceType statisticService;
	
	@ExceptionHandler(value=NullPointerException.class)
	public String glException(NullPointerException e , Model model) throws ServletException{
		System.out.println(exceptionStacktraceToString(e));
		statisticService.persistMethodEvent(e);
		String redirectURL = homepage()+"?error=DataIntegrity";
		request.logout();
		return "redirect:"+redirectURL;
	}

	@ExceptionHandler(value=java.lang.IllegalArgumentException.class)
	public String glException(java.lang.IllegalArgumentException e , Model model) throws ServletException{
		statisticService.persistMethodEvent(e);
		String redirectURL = homepage()+"?error=DataIntegrity";
		request.logout();
		return "redirect:"+redirectURL;
	}
	@ExceptionHandler(value=javax.servlet.ServletException.class)
	public String glException(javax.servlet.ServletException e , Model model) throws ServletException{
		statisticService.persistMethodEvent(e);

		String redirectURL = homepage()+"?error=DataIntegrity";
		request.logout();
		return "redirect:"+redirectURL;
	}
	@ExceptionHandler(value=javax.servlet.jsp.JspTagException.class)
	public String glException(javax.servlet.jsp.JspTagException e , Model model) throws ServletException{
		statisticService.persistMethodEvent(e);

		String redirectURL = homepage()+"?error=DataIntegrity";
		request.logout();
		return "redirect:"+redirectURL;
	}

	String homepage(){
		return	request.getScheme()
				+"://"
				+request.getServerName()
				+":"
				+request.getLocalPort()
				+request.getContextPath();
	}
	
	public static String exceptionStacktraceToString(Exception e)
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    e.printStackTrace(ps);
	    ps.close();
	    return baos.toString();
	}
}
