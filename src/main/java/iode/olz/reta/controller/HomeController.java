package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import iode.olz.reta.dao.RegistrationFormUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	//private final Logger log = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value="/", method = GET)
	public String root(Principal principal) {
		if(principal != null) {
			return "redirect:home";
		} else {
			return "redirect:welcome";
		}
	}
	
	@RequestMapping(value="home/**", method=GET)
	public String getOricApp(Model model) {
		return "oric";
	}
	
	@RequestMapping(value = "/welcome", method=GET)
	public String welcome(Model model) {
		model.addAttribute("user", new RegistrationFormUser());
		return "welcome";
	}

	@RequestMapping(value = "/error", method=GET)
	public String error() {
		return "error";
	}    
}