package marcWeiss.sTracker.component.helper.recurrenceIterator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.TreeSet;

class DaysOfWeekIterator implements RecurrenceIterator {

	DayOfWeek[] daysOfWeek;
	LocalDate lastRecurrence;
	TreeSet<LocalDate> datesTree;

	DaysOfWeekIterator(DayOfWeek[] daysOfWeek, LocalDate lastRecurrence ) {
		this.daysOfWeek = daysOfWeek;
		this.lastRecurrence = lastRecurrence;
		this.datesTree = new TreeSet<>();
	}
	
	public void buildDatesTreee(LocalDate fromDate, boolean nextOrSame){
		datesTree.clear();
		if(!nextOrSame) fromDate = fromDate.plusDays(1);
		for (DayOfWeek day : daysOfWeek) {
			datesTree.add(fromDate.with(TemporalAdjusters.nextOrSame(day)));				
		}
	}

	@Override
	public LocalDate next() {
		buildDatesTreee(lastRecurrence, false);
		lastRecurrence = datesTree.first();
		return lastRecurrence;
	}

	@Override
	public LocalDate nextRecurrenceFromToday() {
		buildDatesTreee(LocalDate.now(),true);
		lastRecurrence = datesTree.first();
		return lastRecurrence;	}

}