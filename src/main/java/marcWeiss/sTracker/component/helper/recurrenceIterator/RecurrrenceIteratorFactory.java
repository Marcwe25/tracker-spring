package marcWeiss.sTracker.component.helper.recurrenceIterator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import marcWeiss.sTracker.entity.jpa.TrackedObject;

public class RecurrrenceIteratorFactory {
	
	public static RecurrenceIterator getRecurrenceIterator(TrackedObject trackedObject) throws NumberFormatException{

			int frequencyType	= trackedObject.getFrequencyType();
			LocalDate lastRecurrence = trackedObject.getLastRecurrence();
			LocalDate nextRecurrence = trackedObject.getNextRecurrence();
			if(lastRecurrence==null) lastRecurrence = LocalDate.now();
			
			switch (frequencyType) {
			
			default :
			case 0:
				
				int numberOfTemporalUnit = Integer.parseInt(trackedObject.getFrequency()[0]);
				ChronoUnit chronoUnit = ChronoUnit.valueOf(trackedObject.getFrequency()[1]);
				return new RepetitiveIterator(numberOfTemporalUnit,chronoUnit,lastRecurrence,nextRecurrence);
				
			case 1:
				
				DayOfWeek[] daysOfWeek = new DayOfWeek[trackedObject.getFrequency().length];
				for (int i = 0; i < daysOfWeek.length; i++) {
						daysOfWeek[i] = DayOfWeek.valueOf(trackedObject.getFrequency()[i].toUpperCase());
					}
				return new DaysOfWeekIterator(daysOfWeek, lastRecurrence);
				
			case 2:
				
				int[] daysOfMonth = new int[trackedObject.getFrequency().length];
				for (int i = 0; i < daysOfMonth.length; i++) {
						daysOfMonth[i] = Integer.parseInt(trackedObject.getFrequency()[i]);
				}
				return new DaysOfMonthIterator(daysOfMonth, lastRecurrence);

			}
	}
}
