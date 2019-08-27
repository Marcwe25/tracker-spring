package marcWeiss.sTracker.component.service;

import java.time.LocalDate;
import java.util.List;

import marcWeiss.sTracker.entity.webEntity.WebHistory;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject.WebTrackerObjectList;

public interface UserServiceInterface {

	public void createTobject(WebTrackerObject webTrackerObject);
		
	public WebTrackerObject getTobject(long id);
	
	public void updadeAllTobjects(List<WebTrackerObject> webTrackerObjects);
	
	public void deleteTObject(long id);
	
	public void addvalue(long objectId, int amount);

	public void addAllvalues(WebTrackerObjectList webTrackerObjectList);

	public void updadeTobject(WebTrackerObject webTrackerObject);

	public WebTrackerObjectList getWebTrackerObjectList(String listType, String category) throws Exception;

	public WebTrackerObjectList getWebTrackerObjectsList() throws Exception;

	public  List<WebHistory> getViewForDay(LocalDate day);

	public int[][] getStatisticForIds(String[] ids);

}
