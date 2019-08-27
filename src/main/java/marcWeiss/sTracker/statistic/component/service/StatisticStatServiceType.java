package marcWeiss.sTracker.statistic.component.service;

import java.util.List;
import java.util.Map;

public interface StatisticStatServiceType {

	List<Map<String, Object>> getGeneraleStatistic();

	List<Map<String, Object>> getExceptionStatistic();

	List<Map<String, Object>> peekDays();

	List<Map<String, Object>> unusedMethod();

	List<Map<String, Object>> peekHours();

	int getTotalFor(List<Map<String, Object>> exceptionStatistic, String key);

	List<Map<String, Object>> rawData();

	List<Map<String, Object>> sessionData(long usageid);

	List<Map<String, Object>> userData(long usageid);

}