package marcWeiss.sTracker.statistic.component.service;

import java.util.List;
import java.util.Set;

import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.beans.MethodEvent;

public interface StatisticServiceType {

	public List<String> showTables();
	
	public void updateDataBase();

	public void addMethodEvent(
			MethodBean method,
			String username,
			String sessionId,
			long duration);

	public void addMethodEvent(
			MethodBean method,
			String username,
			String sessionId,
			long duration,
			Throwable throwable
			);

	public void addMethodEvent(MethodEvent methodEvent);

	public Set<MethodBean> scanForMethod();
	
	public MethodEvent initializeMethodEvent(MethodBean method);

	MethodEvent initializeMethodEvent(Object object);

	public void setDurationTillNow(MethodEvent methodEvent);

	public MethodEvent addException(MethodEvent methodEvent, Throwable e);

	public void persistMethodEvent(Exception e);


}