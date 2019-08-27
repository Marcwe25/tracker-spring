package marcWeiss.sTracker.repositories.credential;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import marcWeiss.sTracker.entity.credentials.Users;

public interface UsersRepository extends Repository<Users, String> {

	@Transactional
	@Modifying
	@Query("insert into users values (:username,:password,true)")
	public void save(@Param("username")String username, @Param("password")String password);

	public List<String> getUsername();
	
	@Transactional
	@Query("select username from users where username=:username")
	public String getUsername(@Param("username")String usernane);
	
	public List<Users> findAll();
	
	@Transactional
	@Modifying
	@Query("update users set enabled = case when enabled=false then true else false end where username=:username")
	public void switchUserEnable(@Param("username")String username);

	@Transactional
	@Modifying
	@Query("delete from users where username=:username")
	public void deleteUser(@Param("username")String username);

}
