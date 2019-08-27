package marcWeiss.sTracker.component.helper.recurrenceIterator;

import java.time.LocalDate;

public interface RecurrenceIterator {

	public LocalDate next();

	public LocalDate nextRecurrenceFromToday();
	
	
}