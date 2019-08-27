package marcWeiss.sTracker.component.controler;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import marcWeiss.sTracker.component.service.CredentialServiceInterface;
import marcWeiss.sTracker.component.validator.NewUserValidator;
import marcWeiss.sTracker.entity.credentials.Authorities;
import marcWeiss.sTracker.entity.webEntity.NewUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	CredentialServiceInterface credentialService;
	
	@Autowired
	NewUserValidator NewUserValidator;
	
	@InitBinder("newUser")
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.setValidator(NewUserValidator);
	}
	
	@GetMapping("/registerForm")
	public String addUserForm(Model model){
		model.addAttribute("newUser", new NewUser());
		return "registerForm";
	}
	
	
	@PostMapping(path="/applyRegistration")
	public String processRegistration(@Valid @ModelAttribute("newUser") NewUser newUser, BindingResult result, Model model){
		if(result.hasErrors()){
			model.addAttribute("newUser", newUser);
			return "registerForm";
		}
		Authorities authorities = new Authorities();
		authorities.username=newUser.username;
		authorities.authority="ROLE_USER";
		credentialService.addUser(newUser, authorities);
		return "redirect:/home?message=registrationSuccessfull";
	}
	



	

}
