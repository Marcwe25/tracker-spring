package marcWeiss.sTracker.component.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.function.IntUnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import marcWeiss.sTracker.component.helper.DataConverter;
import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.entity.jpa.History;
import marcWeiss.sTracker.entity.jpa.TrackedObject;
import marcWeiss.sTracker.entity.webEntity.WebHistory;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject.WebTrackerObjectList;
import marcWeiss.sTracker.repositories.jpa.HistoryRepository;
import marcWeiss.sTracker.repositories.jpa.TObjectRepository;
import marcWeiss.sTracker.statistic.component.service.StatisticServiceType;


@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private DataConverter dataConverter;
	@Autowired
	private TObjectRepository tObjectRepository;
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private PageProperties userPageProperties;
	@Autowired
	private StatisticServiceType statisticService;
	
	@Override
	public void createTobject(WebTrackerObject webTrackerObject) {
		TrackedObject trackedObject = dataConverter.getTrackedObject(webTrackerObject,username());
		trackedObject.setUsername(username());
		tObjectRepository.save(trackedObject);
	}


	@Override
	public void updadeAllTobjects(List<WebTrackerObject> webTrackerObjects) {
		List<TrackedObject> trackedObjects = dataConverter.convertToTrackedObjectList(webTrackerObjects,username());
		for(TrackedObject trackedObject : trackedObjects){
			tObjectRepository.save(trackedObject);			
		}
	}

	@Override
	public void deleteTObject(long id) {
		historyRepository.deleteByTrackedObjectId(id);
		tObjectRepository.deleteById(id);
	}

	@Override
	public void addvalue(long objectId, int amount) {
		History h = new History();
		h.setLocalDateTime(LocalDateTime.now());
		h.setTrackedObject(tObjectRepository.findById(objectId));
		h.setValue(amount);
		tObjectRepository.updateComplete(amount, LocalDate.now(), objectId);
		historyRepository.save(h);
	}

	@Override
	public void addAllvalues(WebTrackerObjectList webTrackerObjectList) {
		webTrackerObjectList.getWebTrackerObjects().stream()
			.filter(w->!w.getAmount().equals("0"))
			.forEach(w-> addvalue(w.getId(),Integer.valueOf(w.getAmount())));
	}

	@Override
	public WebTrackerObject getTobject(long id) {
		TrackedObject t = tObjectRepository.findById(id);
		return dataConverter.getWebTrackerObject(t);
	}

	@Override
	public void updadeTobject(WebTrackerObject webTrackerObject) {
		TrackedObject trackedObject = dataConverter.getTrackedObject(webTrackerObject,username());
		tObjectRepository.save(trackedObject);
	}

	@Override
	public WebTrackerObjectList getWebTrackerObjectList(String listType, String category) throws Exception{
		List<WebTrackerObject> webTrackerObjects = getUserTobject( listType,  category);
		WebTrackerObjectList webTrackerObjectList = new WebTrackerObjectList(webTrackerObjects);
		return webTrackerObjectList;
	}

	public List<WebTrackerObject> getUserTobject(String listType, String category) throws Exception{
		List<WebTrackerObject> list = getUserTobject();
		if(category!=null && !category.equals("all") && !category.equals("")){
			list = dataConverter.byCategory(list, category);
		}
		if(listType!=null && listType.equals("today")){
			list = dataConverter.forToday(list);
		}
		return list;
	}
	
	public String username(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public List<WebTrackerObject> getUserTobject() throws Exception {
		tObjectRepository.resetComplete(LocalDate.now());
		List<TrackedObject> findByUsername = null;

			findByUsername = tObjectRepository.findByUsername(username());
		
		return dataConverter.getWebTrackerObjectList(findByUsername);
	}


	@Override
	public WebTrackerObjectList getWebTrackerObjectsList() throws Exception {
		WebTrackerObjectList webTrackerObjectList = getWebTrackerObjectList(
				userPageProperties.getCurrentList(),
				userPageProperties.getCategory()
				);
		return webTrackerObjectList;
	}


	@Override
	public List<WebHistory> getViewForDay(LocalDate day) {
		List<History> historyList = historyRepository.findAllBetween(Timestamp.valueOf(day.atStartOfDay()), Timestamp.valueOf(day.atTime(LocalTime.MAX)),username());
		List<WebHistory> list = new ArrayList<WebHistory>();
		for(History history : historyList){
			list.add(new WebHistory(history.getTrackedObject().getName(), history.getValue(), history.getLocalDateTime()));
		}
		return list;
	}


	@Override
	public int[][] getStatisticForIds(String[] ids) {
		LocalDate from;
		LocalDate to;
		if(userPageProperties.getFrom().isBefore(userPageProperties.getTo())||userPageProperties.getFrom().equals(userPageProperties.getTo())){
			from = userPageProperties.getFrom();
			to = userPageProperties.getTo();
		}else{
			from = userPageProperties.getTo();
			to = userPageProperties.getFrom();
		}
		
		TreeSet<Integer> idTree = new TreeSet<>();
		for (int i = 0; i < ids.length; i++) {
			idTree.add(Integer.parseInt(ids[i]));
			
		}
		// get history for ids
		int[][] statistics = new int[idTree.size()][Long.valueOf(ChronoUnit.DAYS.between(from, to)).intValue()+1];
		int n = 0 ;
		for (Integer id : idTree) {
			//get histories for object id
			List<History> list = historyRepository.findAllForIdBetween(
					id,
					Timestamp.valueOf(userPageProperties.getFrom().atStartOfDay()),
					Timestamp.valueOf(userPageProperties.getTo().atTime(LocalTime.MAX))
					);
			// arrange and concatenate in array
			int[] arrayForObjectId = initializeArrayForTimeline(from,to);
			arrayForObjectId = putInArray(arrayForObjectId,list,from,to);
			statistics[n++] = arrayForObjectId;
		}
		// arrange in timeline fashion
		return statistics;
	}
	
	

	private int[] putInArray(int[] arrayForObjectId, List<History> list, LocalDate from, LocalDate to) {
		for(int i = 0 ; from.plusDays(i).compareTo(to)>0;i++){
			
		}
		for (History history : list) {
			int n = Long.valueOf(ChronoUnit.DAYS.between(from, history.getLocalDateTime().toLocalDate())).intValue();
			arrayForObjectId[n] += history.getValue();
		}
		return arrayForObjectId;
	}


	private int[] initializeArrayForTimeline(LocalDate from, LocalDate to){
		
		
		int[] ar = new int[Long.valueOf(ChronoUnit.DAYS.between(from, to)).intValue()+1];
		
		Arrays.setAll(ar, new IntUnaryOperator(){
			@Override
			public int applyAsInt(int arg0) {
				return 0;
			}});
		return ar;
	}
	
}
