package marcWeiss.sTracker.component.helper.recurrenceIterator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class RepetitiveIterator implements RecurrenceIterator {
	
	int numberOfTemporalUnit;
	ChronoUnit chronoUnit;
	LocalDate lastRecurrence;
	LocalDate nextRecurrence;

	RepetitiveIterator(	int numberOfTemporalUnit, 
						ChronoUnit chronoUnit,
						LocalDate lastRecurrence,
						LocalDate nextRecurrence
						) {
		
		this.numberOfTemporalUnit = numberOfTemporalUnit;
		this.chronoUnit = chronoUnit;
		this.lastRecurrence = lastRecurrence;
		this.nextRecurrence = nextRecurrence;
		
	}

	@Override
	public LocalDate next() {
		LocalDate targetDate = nextRecurrence.plus(numberOfTemporalUnit, chronoUnit);
		return targetDate;
	}

	@Override
	public LocalDate nextRecurrenceFromToday() {
		LocalDate now = LocalDate.now();
		nextRecurrence = lastRecurrence == null ? now : lastRecurrence;
		while(nextRecurrence.isBefore(now)){
				nextRecurrence = next();
			}			
		return nextRecurrence;
	}
	


}