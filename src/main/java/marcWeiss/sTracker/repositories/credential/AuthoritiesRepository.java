package marcWeiss.sTracker.repositories.credential;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import marcWeiss.sTracker.entity.credentials.Authorities;

public interface AuthoritiesRepository extends Repository<Authorities, Long> {

	@Transactional
	@Modifying
	@Query("insert into authorities (username,authority) values (:username,:authorithy)")
	void save(@Param("username")String username, @Param("authorithy")String authorithy);
	
	public List<Authorities> findAll();

	@Transactional
	@Modifying
	@Query("delete from authorities where username=:username")
	public void deleteUser(@Param("username")String username);

}
