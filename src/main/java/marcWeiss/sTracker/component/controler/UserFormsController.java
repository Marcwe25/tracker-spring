package marcWeiss.sTracker.component.controler;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.component.service.UserServiceInterface;
import marcWeiss.sTracker.component.validator.WebTrackedObjectListValidator;
import marcWeiss.sTracker.component.validator.WebTrackedObjectValidator;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject.WebTrackerObjectList;

@Controller
@RequestMapping("/form")
public class UserFormsController {
	

	@Autowired
	private PageProperties userPageProperties;
	@Value("${days}")
	String[] daysList;
	@Value("${daysPrefix}")
	String[] daysPrefixList;
	@Autowired
	UserServiceInterface uService;
	@Autowired	
	WebTrackedObjectListValidator webTrackedObjectListValidator;
	@Autowired
	WebTrackedObjectValidator WebTrackedObjectValidator;
	
	@InitBinder("webTrackedObjectListCmd")
	protected void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(webTrackedObjectListValidator);
	}
	
	@InitBinder("webTrackerObject")
	protected void initBinder2(WebDataBinder webDataBinder){
		webDataBinder.addValidators(WebTrackedObjectValidator);
	}

	@ModelAttribute
	public void addToModel(Model model){
		model.addAttribute("pageInfo", userPageProperties);
	}

	@GetMapping("/createForm")
	public String createForm(Model model){
		WebTrackerObject webTrackerObject= new WebTrackerObject();
		userPageProperties.setType("create");
		webTrackerObject.setNextRecurrence(LocalDate.now());
		model.addAttribute("daysList", daysList);
		model.addAttribute("daysPrefix", daysPrefixList);
		model.addAttribute("webTrackerObject", webTrackerObject);
		return "createForm";
	}
	
	@PostMapping(path="/create")
	public String createObject(@Valid @ModelAttribute("webTrackerObject")WebTrackerObject webTrackerObject, BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			userPageProperties.setType("create");
			model.addAttribute("daysList", daysList);
			model.addAttribute("daysPrefix", daysPrefixList);
			model.addAttribute("webTrackerObject", webTrackerObject);
			return "createForm"; 	
		}
		System.out.println(webTrackerObject.getName());
		uService.createTobject(webTrackerObject);
		return "redirect:/view/currentList";
	}
	
	@GetMapping("/updateForm")
	public String updateForm(@RequestParam("id") long id, Model model){
		WebTrackerObject webTrackerObject = uService.getTobject(id);
		userPageProperties.setType("update");
		model.addAttribute("webTrackerObject", webTrackerObject);
		model.addAttribute("daysList", daysList);
		model.addAttribute("daysPrefix", daysPrefixList);
		return "createForm";
	}
	
	@PostMapping(path="/update")
	public String update(@Valid @ModelAttribute("webTrackerObject")WebTrackerObject webTrackerObject,BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()){
			userPageProperties.setType("update");
			model.addAttribute("webTrackerObject", webTrackerObject);
			model.addAttribute("daysList", daysList);
			model.addAttribute("daysPrefix", daysPrefixList);
			return "createForm"; 
		}
		uService.updadeTobject(webTrackerObject);
		return "redirect:/view/currentList";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id")long id, HttpServletRequest request){
		uService.deleteTObject(id);
		return "redirect:/view/currentList";
	}

	@PostMapping(path="/increment")
	public String increment(@Valid @ModelAttribute("webTrackedObjectListCmd")WebTrackerObjectList webTrackerObjectList, BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()){
			model.addAttribute("webTrackedObjectListCmd", webTrackerObjectList);
			return "setPage";
		}
		uService.addAllvalues(webTrackerObjectList);
		return "redirect:/view/currentList";
	}
	
	@GetMapping("/setPage")
	public String setPage(Model model) throws Exception{
		WebTrackerObjectList webTrackerObjectList = uService.getWebTrackerObjectsList();
		model.addAttribute("webTrackedObjectListCmd", webTrackerObjectList);
		return "setPage";
	}

}
