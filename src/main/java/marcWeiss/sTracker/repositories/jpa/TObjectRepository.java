package marcWeiss.sTracker.repositories.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import marcWeiss.sTracker.entity.jpa.TrackedObject;

public interface TObjectRepository extends Repository<TrackedObject, Long> {

	TrackedObject save(TrackedObject trackedObject);
	
	TrackedObject findById(Long primaryKey);
	
	List<TrackedObject> findByUsername(String username) throws Exception;
	
	void deleteById(Long id);
	
	@Transactional
	@Modifying
	@Query("update TrackedObject set complete=0 where lastRecurrence<?1")
	int resetComplete(LocalDate date);
	
	@Transactional
	@Modifying
	@Query("update TrackedObject set complete=complete+?1 , lastRecurrence=?2 where id=?3")
	void updateComplete(int amount, LocalDate date, Long id);
}
