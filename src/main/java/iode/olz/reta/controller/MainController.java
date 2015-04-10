package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	//private final Logger log = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value="loops/**", method=GET)
	public String getOricApp(Model model) {
		return "oric";
	}
	
	@RequestMapping(value = "/welcome", method=GET)
	public String welcome(Model model) {				
		return "welcome";
	}

	@RequestMapping(value = "/error", method=GET)
	public String error() {
		return "error";
	}    
}