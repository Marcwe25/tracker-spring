package marcWeiss.sTracker.repositories.jpa;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import marcWeiss.sTracker.entity.jpa.History;

public interface HistoryRepository extends Repository<History, Long> {

	@Transactional
	void deleteByTrackedObjectId(long id);
	
	@Transactional
	void save(History history);
	
	@Transactional
	@Query("from History where Localdatetime BETWEEN :startDate AND :endDate and trackedObject.username=:username")
	List<History> findAllBetween(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate,@Param("username")String username);

	@Transactional
	@Query("from History where Localdatetime BETWEEN :from AND :to and trackedObject.id=:id")
	List<History> findAllForIdBetween(@Param("id")long id, @Param("from")Timestamp from, @Param("to")Timestamp to);

}
