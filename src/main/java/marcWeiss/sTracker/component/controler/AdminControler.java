package marcWeiss.sTracker.component.controler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import marcWeiss.sTracker.component.helper.AdminRole;
import marcWeiss.sTracker.component.helper.PageProperties;
import marcWeiss.sTracker.component.service.CredentialServiceInterface;
import marcWeiss.sTracker.entity.webEntity.WebUser;
import marcWeiss.sTracker.statistic.component.service.StatisticStatServiceType;

@Controller
@RequestMapping(value = "/adminPage")
@AdminRole
public class AdminControler {

	@Autowired
	StatisticStatServiceType statService;

	@Autowired
	CredentialServiceInterface credentialService;

	@Autowired()
	PageProperties adminStatpage;

	@ModelAttribute
	public void addAttribute(Model model) {
		model.addAttribute("pageProperty", adminStatpage);
		model.addAttribute("switchCompact", adminStatpage.isCompact()?"full":"compact");
	}

	@RequestMapping("/users")
	public String usersPage(Model model) {
		List<WebUser> webUsers = credentialService.getWebUserList();
		model.addAttribute("webUsers", webUsers);
		return "users";
	}

	@GetMapping("/generalStat")
	public String generalStat(Model model) {
		adminStatpage.setCurrentList("generalStat");
		List<Map<String, Object>> generaleStatistic = statService.getGeneraleStatistic();
		model.addAttribute("stat", generaleStatistic);
		return "adminStat";

	}

	@GetMapping("/exceptionStat")
	public String exceptionCount(Model model) {
		adminStatpage.setCurrentList("exceptionStat");
		List<Map<String, Object>> exceptionStatistic = statService.getExceptionStatistic();
		int total = statService.getTotalFor(exceptionStatistic, "total");
		model.addAttribute("stat", exceptionStatistic);
		model.addAttribute("total", total);
		return "adminStat";
	}

	@GetMapping("/peekDays")
	public String peekDays(Model model) {
		adminStatpage.setCurrentList("peekDays");
		List<Map<String, Object>> stat = statService.peekDays();
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/peekHours")
	public String peekHours(Model model) {
		adminStatpage.setCurrentList("peekHours");
		List<Map<String, Object>> stat = statService.peekHours();
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/unused")
	public String unusedMethod(Model model) {
		adminStatpage.setCurrentList("unused");
		List<Map<String, Object>> stat = statService.unusedMethod();
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/raw")
	public String rawData(Model model) {
		if (adminStatpage!=null && !adminStatpage.getCurrentList().equals("raw")) {
			adminStatpage.setPagingValue(0);
		}
		adminStatpage.setCurrentList("raw");
		List<Map<String, Object>> stat = statService.rawData();
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/session/{usageid}")
	public String sessionData(@PathVariable("usageid") long usageid, Model model) {
		if (!adminStatpage.getCurrentList().equals("session")) {
			adminStatpage.setUsageid(0);
			adminStatpage.setPagingValue(0);
		}
		adminStatpage.setUsageid(usageid);
		adminStatpage.setCurrentList("session");
		List<Map<String, Object>> stat = statService.sessionData(usageid);
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/session")
	public String sessionData(Model model) {
		return "redirect:session/" + adminStatpage.getUsageid();
	}

	@GetMapping("/user/{usageid}")
	public String userData(@PathVariable("usageid") long usageid, Model model) {
		if (!adminStatpage.getCurrentList().equals("user")) {
			adminStatpage.setUsageid(0);
			adminStatpage.setPagingValue(0);
		}
		adminStatpage.setUsageid(usageid);
		adminStatpage.setCurrentList("user");

		List<Map<String, Object>> stat = statService.userData(usageid);
		model.addAttribute("stat", stat);
		return "adminStat";
	}

	@GetMapping("/user")
	public String userData(Model model) {
		return "redirect:user/" + adminStatpage.getUsageid();
	}

	@RequestMapping("/disable")
	public String disable(@RequestParam(name = "username") String username, Model model) {
		if (!(username.equals("superAdmin") || username.equals("admin"))) {
			credentialService.switchUserEnable(username);
			List<WebUser> webUsers = credentialService.getWebUserList();
			model.addAttribute("webUsers", webUsers);
		}
		return usersPage(model);
	}

	@PreAuthorize("hasRole('ADMIN_WRITE')")
	@RequestMapping("/delete")
	public String delete(@RequestParam(name = "username") String username, Model model) {
		credentialService.deleteUser(username);
		List<WebUser> webUsers = credentialService.getWebUserList();
		model.addAttribute("webUsers", webUsers);
		return usersPage(model);
	}

	@PostMapping(path = "/filterDate")
	public String setDateToFilter(@ModelAttribute("pageProperty") PageProperties pageProperties, Model model) {
		adminStatpage.setFrom(pageProperties.getFrom());
		adminStatpage.setTo(pageProperties.getTo());
		return "redirect:" + adminStatpage.getCurrentList();
	}

	@GetMapping("/next")
	public String nextResult() {
		adminStatpage.setPagingValue(adminStatpage.getPagingValue() + 10);
		return "redirect:" + adminStatpage.getCurrentList();
	}

	@GetMapping("/previous")
	public String previousResult() {
		if ((adminStatpage.getPagingValue() - 10) >= 0) {
			adminStatpage.setPagingValue(adminStatpage.getPagingValue() - 10);
		}
		return "redirect:" + adminStatpage.getCurrentList();
	}
	
	@GetMapping("/setCompact")
	public String setCompact(@RequestParam("compact")Boolean compact, Model model){
		if(compact!=null){
			adminStatpage.setCompact(compact);
		}
		return "redirect:"+adminStatpage.getCurrentList();
	}
}
