package marcWeiss.sTracker.statistic.component.repository;

import java.util.List;

import marcWeiss.sTracker.statistic.beans.MethodBean;
import marcWeiss.sTracker.statistic.beans.MethodEvent;

public interface MethodRepositoryType {

	List<String> showTables();

	void insert(MethodBean methodBean);

	boolean contain(MethodBean methodBean);

	void addMethodEvent(MethodBean method, String username, String sessionId, long duration, Throwable throwable);

	void addMethodEvent(MethodBean methodBean, String username, String sessionId, long duration);

	void addMethodEvent(MethodBean methodBean, String username, String sessionId, long duration, String exType,
			String exMessage, String exCause);
	
	void setExist(boolean exist, MethodBean methodBean);
	
	boolean isExist(MethodBean methodBean);

	void setAllMethodExist(boolean b);

	void addMethodEvent(MethodEvent methodEvent);

	List<MethodBean> findSimilar(MethodBean methodBean);
	
}