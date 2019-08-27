package marcWeiss.sTracker.entity.credentials;

import org.springframework.data.annotation.Id;

public class Authorities {

	@Id
	public Long id;
	public String username;
	public String authority;
	
	
}
