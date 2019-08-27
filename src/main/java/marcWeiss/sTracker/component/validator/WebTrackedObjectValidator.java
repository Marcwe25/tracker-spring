package marcWeiss.sTracker.component.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;

@Component
public class WebTrackedObjectValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return WebTrackerObject.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String onlyDigitRegex = "^\\d{1,}$";
		String from1To31Regex = "([1-9]|[12][0-9]|3[01])";
		WebTrackerObject o = (WebTrackerObject) target;
		String[][] frequency = o.getFrequency();
		if (frequency[0] != null) {
			if (!frequency[0][0].matches(onlyDigitRegex))
				errors.rejectValue("frequency[0][0]", "typeMismatch.int");
			if (!(frequency[0][1].equals("DAYS")
					||frequency[0][1].equals("WEEKS")
					||frequency[0][1].equals("MONTHS"))){
				errors.rejectValue("frequency[0][0]", "choose.fromMenu");}
		}
		
		if(frequency[1] != null){
			for (String dayInput : frequency[1]) {
				if(!isWeekDay(dayInput)){
					errors.rejectValue("frequency[1]", "choose.weekday");}
				}
		}
		
		if(frequency[2]!=null){
			for (String dayInput : frequency[2]) {
				if(!dayInput.matches(from1To31Regex)){
					errors.rejectValue("frequency[2]", "choose.monthday");}
				}
		}
	}
	
	public boolean isWeekDay(String day){
		return 
		  day.equals("Monday")
		||day.equals("Tuesday")
		||day.equals("Wednesday")
		||day.equals("Thursday")
		||day.equals("Friday")
		||day.equals("Saturday")
		||day.equals("Sunday");
	}

}
