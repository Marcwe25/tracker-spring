package marcWeiss.sTracker.entity.credentials;

import org.springframework.data.annotation.Id;

public class Users {

	@Id
	public String username;
	public String password;
	public boolean enabled;
	
}
