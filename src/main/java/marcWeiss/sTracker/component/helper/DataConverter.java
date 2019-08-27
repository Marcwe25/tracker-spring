package marcWeiss.sTracker.component.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import marcWeiss.sTracker.entity.credentials.Authorities;
import marcWeiss.sTracker.entity.credentials.Users;
import marcWeiss.sTracker.entity.jpa.TrackedObject;
import marcWeiss.sTracker.entity.webEntity.NewUser;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;
import marcWeiss.sTracker.entity.webEntity.WebUser;


@Component
public class DataConverter {

	@Autowired
	PasswordEncoder bcryptEncoder;
	
	private String[] defaultFrequency = new String[]{"1","DAYS"};
	
	
	public TrackedObject getTrackedObject(WebTrackerObject webTrackerObject, String username){
		TrackedObject trackedObject = new TrackedObject();
		trackedObject.setFrequency(convertFrequency(webTrackerObject));
		trackedObject.setCategory(webTrackerObject.getCategory());
		trackedObject.setComplete(webTrackerObject.getComplete());
		trackedObject.setFrequencyType(webTrackerObject.getFrequencyType());
		trackedObject.setHistories(webTrackerObject.getHistories());
		trackedObject.setId(webTrackerObject.getId());
		trackedObject.setImportant(webTrackerObject.isImportant());
		trackedObject.setLastRecurrence(webTrackerObject.getLastRecurrence());
		trackedObject.setName(webTrackerObject.getName());
		trackedObject.setNextRecurrence(webTrackerObject.getNextRecurrence());
		trackedObject.setTarget(webTrackerObject.getTarget());
		trackedObject.setValue(webTrackerObject.getValue());
		trackedObject.setUsername(username);
		return trackedObject;
	}
	
	public WebTrackerObject getWebTrackerObject(TrackedObject trackedObject){
		try {
			WebTrackerObject webTrackerObject = new WebTrackerObject();
			webTrackerObject.setFrequency(convertToWebFrequency(trackedObject));
			webTrackerObject.setCategory(trackedObject.getCategory());
			webTrackerObject.setFrequencyType(trackedObject.getFrequencyType());
			webTrackerObject.setId(trackedObject.getId());
			webTrackerObject.setImportant(trackedObject.isImportant());
			webTrackerObject.setLastRecurrence(trackedObject.getLastRecurrence());
			webTrackerObject.setName(trackedObject.getName());
			webTrackerObject.setNextRecurrence(nextRecurrenceFromToday(trackedObject));
			webTrackerObject.setComplete(trackedObject.getComplete());
			webTrackerObject.setTarget(trackedObject.getTarget());
			webTrackerObject.setValue(trackedObject.getValue());
			webTrackerObject.setHistories(trackedObject.getHistories());
			webTrackerObject.setAmount("0");
			return webTrackerObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<WebTrackerObject> getWebTrackerObjectList(List<TrackedObject> trackedObjects){
		List<WebTrackerObject> webTrackerObjects = trackedObjects.stream()
													.map(this::getWebTrackerObject)
													.collect(Collectors.toList());
		return webTrackerObjects;
	}
	
	public List<TrackedObject> convertToTrackedObjectList(List<WebTrackerObject> webTrackerObjects, String username){
		List<TrackedObject> trackedObjects = webTrackerObjects.stream()
				.map(webTrackerObject->getTrackedObject(webTrackerObject,username))
				.collect(Collectors.toList());
		return trackedObjects;
	}
	
	private String[] convertFrequency(WebTrackerObject webTrackerObject){
		int type = webTrackerObject.getFrequencyType();
		String[] frequency = webTrackerObject.getFrequency()[type];
		
		if(frequency==null
			|| frequency.length==0
			|| frequency[0]==null
			|| frequency[0].equals("")) {
			
			frequency = new String[]{"1","DAYS"};
			webTrackerObject.setFrequencyType(0);
		}

		return frequency;
	}

	private String[][] convertToWebFrequency(TrackedObject trackedObject){
		int type = trackedObject.getFrequencyType();
		if(type<0 || type> 2){
			type = 0;
			trackedObject.setFrequencyType(0);
		}
		String[][] frequency = new String[3][];
		frequency[0] = defaultFrequency;
		frequency[1] = new String[0];
		frequency[2] = new String[0];
		String[] tmp = trackedObject.getFrequency();
		if(tmp == null||
				tmp.length==0||
				tmp[0]==null ||
				tmp[0].equals("")) tmp = defaultFrequency;
		frequency[type] = tmp;
		return frequency;
	}
	
	private LocalDate nextRecurrenceFromToday(TrackedObject t){
		LocalDate nextRecurrenceFromToday = null;
		nextRecurrenceFromToday = t.getRecurrenceIterator().nextRecurrenceFromToday();
		return nextRecurrenceFromToday;
		
	}
	
	public List<WebTrackerObject> forToday(List<WebTrackerObject> webTrackerObjects) {
		LocalDate now = LocalDate.now();
		List<WebTrackerObject> lr = webTrackerObjects.stream()
				.filter(t->t.getNextRecurrence().isEqual(now))
				.collect(Collectors.toList());
		return lr;
	}
	
	public List<WebTrackerObject> byCategory(List<WebTrackerObject> webTrackerObjects,String category){
		List<WebTrackerObject> list = webTrackerObjects.stream().
				filter(t->t.getCategory().equals(category)).
				collect(Collectors.toList());
		return list;
	}
	
	public Users getUser(NewUser newUser){
		Users user = new Users();
		user.enabled=true;
		user.username=newUser.username;
		user.password=bcryptEncoder.encode(newUser.password);
		
		return user;
	}

	public WebUser getWebUser(Users user,List<String> authorithies){
		
		WebUser webUser = new WebUser();
		webUser.setUsername(user.username);
		webUser.setEnabled(user.enabled);
		webUser.setAuthorities(authorithies);
		return webUser;
	}
	
	public List<WebUser> getWebUserList(List<Users> users, List<Authorities> authorities){
		List<WebUser> webUsers = new ArrayList<>();
		TreeMap<String, List<String>> authoritiesByUsername = authoritiesByUsername(authorities);
		for(Users user : users){
			WebUser webUser = getWebUser(user, authoritiesByUsername.get(user.username));
			webUsers.add(webUser);
		}
		return webUsers;
	}
	
	public TreeMap<String, List<String>> authoritiesByUsername(List<Authorities> authorities){
		
		TreeMap<String, List<String>> authoritiesByUsername = new TreeMap<>();
		for(Authorities authority : authorities){
			String username = authority.username;
			List<String> userAuthorities = authoritiesByUsername.get(username);
			if(userAuthorities == null) {
				authoritiesByUsername.put(username, new ArrayList<>(Arrays.asList(authority.authority)));
			} else{
				try {
					userAuthorities.add(authority.authority);
				} catch (Exception e) {
					System.err.println(":::"+authority.authority);
					System.err.println(userAuthorities.size());
					e.printStackTrace();
				}
			}
		}
		return authoritiesByUsername;
	}
}
