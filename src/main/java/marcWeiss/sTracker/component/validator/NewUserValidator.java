package marcWeiss.sTracker.component.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import marcWeiss.sTracker.component.service.CredentialServiceInterface;
import marcWeiss.sTracker.entity.webEntity.NewUser;

@Component
public class NewUserValidator implements Validator {

	@Autowired
	CredentialServiceInterface credentialService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NewUser.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		
		NewUser newUser = (NewUser)arg0;
		
		//validate username and password are not empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.Empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.Empty");

		//validate confirmation password match password
		if(!newUser.getPassword().equals(newUser.getConfirmpassword()))
			errors.rejectValue("password", "password.doesntmatch");
		
		//validate that username is not already taken
		if(credentialService.userExist(newUser.getUsername())){
			errors.rejectValue("username", "user.exist");
		}

		//validate that username is 'anonimousUser'
		if(newUser.getUsername().equals("anonimousUser")){
			
			errors.rejectValue("username", "user.anonimousInvalid");
		}
	}

}
