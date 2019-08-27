package marcWeiss.sTracker.component.controler;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.component.service.UserServiceInterface;
import marcWeiss.sTracker.entity.webEntity.WebTrackerObject.WebTrackerObjectList;

@Controller
@RequestMapping("/chart")
public class UserChartController {
	
	@Autowired
	private PageProperties userPageProperties;

	@Autowired
	UserServiceInterface uService;

	@ModelAttribute
	public void addToModel(Model model){
		model.addAttribute("pageInfo", userPageProperties);
	}

	@GetMapping("/jqPlot")
	public String chartForm(Model model) throws Exception{
		WebTrackerObjectList listObject = uService.getWebTrackerObjectList("everyday","all");
		userPageProperties.setTo(LocalDate.now());
		userPageProperties.setFrom(LocalDate.now().minusDays(7));
		model.addAttribute("listObject", listObject);
		model.addAttribute("userPageProperties", userPageProperties);
		return "jqPlot";
	}
	
	@RequestMapping(path="/plotChart", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody int[][] plotChart(@RequestBody String[] ids){
		int[][] stats = uService.getStatisticForIds(ids);
		return stats;
	}

	@RequestMapping(path="/restDateFilter", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String restDateFilter(@RequestBody HashMap<String,Object> jobj){
		LocalDate date = LocalDate.parse((String)jobj.get("date"));
		if(jobj.get("id").equals("#startDate")){
			userPageProperties.setFrom(date);
		}
		if(jobj.get("id").equals("#endDate")){
			userPageProperties.setTo(date);
		}
		return "success";
	}
}
