package marcWeiss.sTracker.statistic.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import marcWeiss.sTracker.statistic.component.service.StatisticServiceType;

@Component
public class Listener {

	@Autowired
	StatisticServiceType statisticService;
	@EventListener()
	public void handleContextRefreshEvent(ContextRefreshedEvent refreshedEvent) throws ClassNotFoundException {
		if (refreshedEvent.getSource().toString().startsWith("Root WebApplicationContext")) {
			System.out.println("running statistic update");
			statisticService.updateDataBase();
		}
	}

}
