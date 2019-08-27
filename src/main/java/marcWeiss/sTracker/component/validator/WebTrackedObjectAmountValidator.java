package marcWeiss.sTracker.component.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;

@Component
public class WebTrackedObjectAmountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return WebTrackerObject.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String onlyDigitRegex = "^\\d{1,}$";
		WebTrackerObject o = (WebTrackerObject) target;
		if (!o.getAmount().matches(onlyDigitRegex)) {
			errors.rejectValue("amount", "typeMismatch.int");
		}
	}

}
