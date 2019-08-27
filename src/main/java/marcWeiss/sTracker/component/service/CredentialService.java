package marcWeiss.sTracker.component.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marcWeiss.sTracker.component.helper.DataConverter;
import marcWeiss.sTracker.entity.credentials.Authorities;
import marcWeiss.sTracker.entity.credentials.Users;
import marcWeiss.sTracker.entity.webEntity.NewUser;
import marcWeiss.sTracker.entity.webEntity.WebUser;
import marcWeiss.sTracker.repositories.credential.AuthoritiesRepository;
import marcWeiss.sTracker.repositories.credential.UsersRepository;

@Service
public class CredentialService implements CredentialServiceInterface{

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	DataConverter dataConverter;
	
	public boolean addUser(NewUser newUser , Authorities authorities){
		Users user = dataConverter.getUser(newUser);
		usersRepository.save(user.username,user.password);
		authoritiesRepository.save(authorities.username,authorities.authority);
		return true;
	}
	
	public List<WebUser> getWebUserList(){
		List<Authorities> authorities = authoritiesRepository.findAll();
		List<Users> users = usersRepository.findAll();
		List<WebUser> webUsers = dataConverter.getWebUserList(users, authorities);
		return webUsers;
	}
	
	public void switchUserEnable(String username){
		usersRepository.switchUserEnable(username);
	}

	@Override
	public void deleteUser(String username) {
		authoritiesRepository.deleteUser(username);
		usersRepository.deleteUser(username);
		
	}

	@Override
	public boolean userExist(String username) {
		String test = usersRepository.getUsername(username);
		return test!=null;
	}
}
