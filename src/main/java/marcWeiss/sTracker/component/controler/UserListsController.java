package marcWeiss.sTracker.component.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.component.service.UserServiceInterface;
import marcWeiss.sTracker.entity.webEntity.WebHistory;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject;

/**
 * @author Marc Weiss
 *
 * Controller class to show info on user's tracked object
 */
@Controller
@RequestMapping("/view")
public class UserListsController {
	
	@Autowired
	UserServiceInterface uService;

	@Autowired
	private PageProperties userPageProperties;
	
	
	/**
	 *** add session scoped page properties to the model
	 **/
	@ModelAttribute
	public void addToModel(Model model){
		model.addAttribute("pageInfo", userPageProperties);
	}

	/**
	 * @param model
	 * @return the name of the view presenting list of tracked object
	 * @throws Exception 
	 */
	@GetMapping("/currentList")
	public String currentList(Model model) throws Exception{
		List<WebTrackerObject> ls = uService.getWebTrackerObjectsList().getWebTrackerObjects();
		model.addAttribute("objectList", ls);
		return "myList";
	}

	/**
	 *** filter tracked objects in the list,
	 *** used to show objects with next recurrence only today or not
	 * @return redirect to currentList handler method
	 */
	@GetMapping("/switch")
	public String switchEveryday(){
		userPageProperties.setCurrentList(userPageProperties.getToggleTo());
		return "redirect:currentList";
	}
	
	/**
	 *** filter tracked objects in the list,
	 *** used to show objects from specific category
	 * @return redirect to currentList handler method
	 */
	@GetMapping("/listCategory")
	public String getListForCategory(@RequestParam("category")String category, Model model){
		if(category!=null){
			userPageProperties.setCategory(category);
		}
		return "redirect:currentList";
	}

	/**
	 *** used to show history of added value
	 * @return the name of the view presenting history of added value
	 */
	@GetMapping("/history")
	public String viewHistory(Model model){
		List<WebHistory> history = uService.getViewForDay(userPageProperties.getFrom());
		userPageProperties.setType("history");
		model.addAttribute("history", history);
		return "history";
		
	}
	
	/**
	 *** used to navigate to previous day in history view
	 * @return redirect to currentList handler method
	 */
	@GetMapping("/before")
	public String viewHistoryBefore(Model model){
		userPageProperties.setFrom(userPageProperties.getFrom().minusDays(1));
		return "redirect:history";
		
	}

	/**
	 *** used to navigate to next day in history view
	 * @return redirect to currentList handler method
	 */
	@GetMapping("/after")
	public String viewHistoryAfter(Model model){
		userPageProperties.setFrom(userPageProperties.getFrom().plusDays(1));
		return "redirect:history";
	}

	/**
	 *** used to navigate to a specific day in history view
	 * @return redirect to currentList handler method
	 */
	@PostMapping(path="/for")
	public String viewHistoryFor(@ModelAttribute("userPageProperties") PageProperties userPage, Model model){
		userPageProperties.setFrom(userPage.getFrom());
		return "redirect:history";
	}
	
}
