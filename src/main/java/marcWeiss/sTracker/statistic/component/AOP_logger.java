package marcWeiss.sTracker.statistic.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.beans.MethodEvent;
import marcWeiss.sTracker.statistic.component.service.StatisticServiceType;


@Aspect
@Component
public class AOP_logger {

	@Autowired
	StatisticServiceType StatisticService;
	
	@Around("execution(* marcWeiss.sTracker.component..*(..))")
	public Object aroundMethod(ProceedingJoinPoint point) throws Throwable{
		MethodBean method = new MethodBean(point.getSignature());
		MethodEvent methodEvent = StatisticService.initializeMethodEvent(method);
		try {
			Object result = point.proceed();
			StatisticService.setDurationTillNow(methodEvent);
			StatisticService.addMethodEvent(methodEvent);
			return result;
		} catch (Throwable e) {
			StatisticService.setDurationTillNow(methodEvent);
			StatisticService.addException(methodEvent, e);
			StatisticService.addMethodEvent(methodEvent);
			throw e;
		}
	}

}
