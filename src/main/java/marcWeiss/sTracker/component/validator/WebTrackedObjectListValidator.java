package marcWeiss.sTracker.component.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject.WebTrackerObjectList;

@Component
public class WebTrackedObjectListValidator implements Validator {

	@Autowired
	WebTrackedObjectAmountValidator webTrackedObjectAmountValidator;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return WebTrackerObjectList.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		String onlyDigitReg = "^\\d{1,}$";
		WebTrackerObjectList list = (WebTrackerObjectList) target;

		for (int i = 0; i < list.getWebTrackerObjects().size(); i++) {
			WebTrackerObject o = list.getWebTrackerObjects().get(i);
			if (!o.getAmount().matches(onlyDigitReg)) {
				errors.pushNestedPath("webTrackerObjects["+i+"]");
				ValidationUtils.invokeValidator(webTrackedObjectAmountValidator, o, errors);
				errors.popNestedPath();
			}
		}
	}

}
