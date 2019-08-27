package marcWeiss.sTracker.component.controler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import marcWeiss.sTracker.component.helper.Utility;

@Controller
public class RootController {

	@RequestMapping("/home")
	public String home(Authentication authentication){
		return Utility.determineTargetUrl();
	}
	
	@RequestMapping("/")
	public String index(Authentication authentication){
		return Utility.determineTargetUrl();
	}
}
