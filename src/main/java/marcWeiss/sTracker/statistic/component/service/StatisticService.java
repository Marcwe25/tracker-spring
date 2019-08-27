package marcWeiss.sTracker.statistic.component.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import marcWeiss.sTracker.component.helper.ParametersFinder;
import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.beans.MethodEvent;
import marcWeiss.sTracker.statistic.component.repository.MethodRepositoryType;

@Service
public class StatisticService implements StatisticServiceType {

	@Autowired
	MethodRepositoryType methodRepository;

	@Autowired
	ParametersFinder reflectionUtility;
	@Override
	public List<String> showTables() {
		return methodRepository.showTables();
	}

	@Override
	public void updateDataBase() {
		Set<MethodBean> methodBeans = scanForMethod();
		methodRepository.setAllMethodExist(false);
		for (MethodBean methodBean : methodBeans) {
			if (!methodRepository.contain(methodBean)) {
				methodRepository.insert(methodBean);
			} else {
				methodRepository.setExist(true, methodBean);
			}
		}
	}

	@Override
	public Set<MethodBean> scanForMethod() {
		Set<MethodBean> methodBeans = new HashSet<>();
		Set<BeanDefinition> beanDefinitions = null;
		try {
			beanDefinitions = scanPackageForSubtype("marcWeiss/sTracker/component", Object.class, false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		for (BeanDefinition b : beanDefinitions) {
			Class<?> c;
			try {
				
				c = Class.forName(b.getBeanClassName());
				Method[] methods = c.getDeclaredMethods();
				for (Method method : methods) {
					if (!method.isSynthetic() && !method.getName().equals("main")) {
						methodBeans.add(new MethodBean(method));
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return methodBeans;
	}

	public static Set<BeanDefinition> scanPackageForSubtype(String packageName, Class<?> classToScan,
			boolean includeSuperClass) throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(Object.class));

		Set<BeanDefinition> components = provider.findCandidateComponents(packageName);
		return components;
	}

	@Override
	public void addMethodEvent(MethodBean method, String username, String sessionId, long duration) {
		methodRepository.addMethodEvent(method, username, sessionId, duration);
	}

	@Override
	public void addMethodEvent(MethodBean method, String username, String sessionId, long duration,
			Throwable throwable) {
		methodRepository.addMethodEvent(method, username, sessionId, duration, throwable);

	}

	@Override
	public MethodEvent initializeMethodEvent(MethodBean method) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = (authentication instanceof AnonymousAuthenticationToken || authentication == null)
				? "anonimousUser" : authentication.getName();
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		MethodEvent event = new MethodEvent();
		event.setMethodBean(method);
		event.setDateTime(LocalDateTime.now());
		event.setSessionId(sessionId);
		event.setUserid(username);
		return event;
	}

	@Override
	public MethodEvent initializeMethodEvent(Object object) {
		MethodBean method = new MethodBean(object.getClass().getEnclosingMethod());
		return initializeMethodEvent(method);
	}

	@Override
	public void setDurationTillNow(MethodEvent methodEvent) {
		if (methodEvent.getDateTime() != null) {
			long duration = Duration.between(methodEvent.getDateTime(), LocalDateTime.now()).toMillis();
			methodEvent.setDuration(duration);
		}
	}

	@Override
	public void addMethodEvent(MethodEvent methodEvent) {
		methodRepository.addMethodEvent(methodEvent);
	}

	@Override
	public MethodEvent addException(MethodEvent methodEvent, Throwable e) {
		if (methodEvent != null) {
			if (e != null) {
				methodEvent.setExceptionType(e.getClass().getName());
				if (e.getCause() != null) {
					methodEvent.setExceptionCause(e.getCause().getClass().getName());
				}
				methodEvent.setExceptionMessage(e.getMessage());
			}
		}
		return methodEvent;
	}

	@Override
	public void persistMethodEvent(Exception e) {
		StackTraceElement stackTraceElement = chooseStackTraceElement(e.getStackTrace());
		MethodBean methodBean = getMethodBean(stackTraceElement);
		MethodEvent event = initializeMethodEvent(methodBean);
		event = addException(event, e);
		addMethodEvent(event);
		System.out.println("PERSISTING EVENT IN METHOD :: " + methodBean.getSignature());
	}

	public MethodBean getMethodBean(StackTraceElement element){
		MethodBean m = new MethodBean();
		try {
		m.setMethodName(element.getMethodName());
		String fullClassName = element.getClassName();
		m.setClassName(fullClassName.substring(fullClassName.lastIndexOf(".")+1));
		m.setPackageName(fullClassName.substring(0, fullClassName.lastIndexOf(".")));
		m.setMethodParameters(reflectionUtility.searchParams(element));
		System.out.println("---got params" + m.getMethodParameters());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	private StackTraceElement chooseStackTraceElement(StackTraceElement[] stackTrace){
	
		for (int i = 0; i < stackTrace.length; i++) {
			System.out.println(stackTrace[i]);
			if(stackTrace[i].getClassName().startsWith("marcWeiss.sTracker.component.")
					&& !stackTrace[i].toString().contains("$")
					){
				System.out.println("RETURNING :::" + stackTrace[i]);
				return stackTrace[i];
				}
		}
		
		return null;
		
	}

}
