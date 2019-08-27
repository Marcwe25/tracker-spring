package marcWeiss.sTracker.component.service;

import java.util.List;

import marcWeiss.sTracker.component.helper.AdminRole;
import marcWeiss.sTracker.entity.credentials.Authorities;
import marcWeiss.sTracker.entity.webEntity.NewUser;
import marcWeiss.sTracker.entity.webEntity.WebUser;

public interface CredentialServiceInterface {

	public boolean addUser(NewUser newUser , Authorities authorities);

	@AdminRole
	public List<WebUser> getWebUserList();
	
	
	@AdminRole
	public void switchUserEnable(String username);

	@AdminRole
	public void deleteUser(String username);

	boolean userExist(String username);
	
}
