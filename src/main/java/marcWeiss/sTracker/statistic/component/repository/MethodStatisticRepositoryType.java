package marcWeiss.sTracker.statistic.component.repository;

import java.util.List;
import java.util.Map;

public interface MethodStatisticRepositoryType {

	List<Map<String, Object>> getGeneralStatistic();

	List<Map<String, Object>> getExceptionStatistic();

	List<Map<String, Object>> getpeekDays();

	List<Map<String, Object>> unusedMethod();

	List<Map<String, Object>> getpeekHours();

	List<Map<String, Object>> rawUsage();

	List<Map<String, Object>> bySession(long usageid);

	List<Map<String, Object>> byUser(long usageid);

}
