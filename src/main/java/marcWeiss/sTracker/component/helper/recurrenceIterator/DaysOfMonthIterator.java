package marcWeiss.sTracker.component.helper.recurrenceIterator;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.TreeSet;

class DaysOfMonthIterator implements RecurrenceIterator {
	
	int[] days;
	TreeSet<LocalDate> datesTree;
	LocalDate lastRecurrence;

	DaysOfMonthIterator(int[] days, LocalDate lastRecurrence) {
		this.days = days;
		this.lastRecurrence = lastRecurrence;
		this.datesTree = new TreeSet<>();
	}
	
	@Override
	public LocalDate next() {
		
		buildDatesTree(lastRecurrence, false);
		lastRecurrence = datesTree.first();
		return lastRecurrence;
	}
	
	@Override
	public LocalDate nextRecurrenceFromToday(){
		
		buildDatesTree(LocalDate.now(), true);
		lastRecurrence = datesTree.first();
		return lastRecurrence;	
	}

	public void buildDatesTree(LocalDate lastRecurrence, boolean nextOrSame) {
		datesTree.clear();
		for (Integer day : days) {
			try{
				LocalDate date = nextDateWithMonthDay(lastRecurrence, day, nextOrSame);
				datesTree.add(date);
			} catch (java.time.DateTimeException e){
				System.err.println(e.getMessage());
			} 
			
		}
	}
	
	
	public LocalDate nextDateWithMonthDay(LocalDate fromDate, int day, boolean nextOrSame){

		if( day>31||day<1 ) throw new java.time.DateTimeException("stracker error: day not in range for use in any month");
		LocalDate nextDate = fromDate;
		
		while(!nextDate.range(ChronoField.DAY_OF_MONTH).isValidValue(day)){
			nextDate = nextDate.with(TemporalAdjusters.firstDayOfNextMonth());
		}
		
		nextDate = nextDate.withDayOfMonth(day);
		
		if(nextOrSame && nextDate.isEqual(fromDate)) return nextDate;
		
		if(!nextDate.isAfter(fromDate)) nextDate = 
				nextDateWithMonthDay(nextDate.with(TemporalAdjusters.firstDayOfNextMonth()), day, true);
		
		return nextDate;
	}
	



}