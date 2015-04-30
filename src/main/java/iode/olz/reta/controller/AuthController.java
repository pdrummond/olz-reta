package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import iode.olz.reta.dao.OlzUser;
import iode.olz.reta.dao.RegistrationFormUser;
import iode.olz.reta.repo.OlzUserRepository;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
	private final Logger log = Logger.getLogger(AuthController.class);
	
	@Autowired
	OlzUserRepository userRepo;
	
	@RequestMapping(value = "/heartbeat", method = POST)    
	public @ResponseBody String heartbeat() {
		return "ok";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginForm(Model model) {
		return "login";
	}

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute("user", new RegistrationFormUser());
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@Transactional
	public String registerSubmit(@ModelAttribute RegistrationFormUser userFormData, Model model, HttpServletRequest request, Principal principal) {
		log.info("Registering user " + userFormData.getUserId() + ".");

		//For optional fields, if they are empty make them null so they aren't used.
		if(StringUtils.isEmpty(userFormData.getFirstName())) { userFormData.setFirstName(null);}
		if(StringUtils.isEmpty(userFormData.getSurname())) { userFormData.setSurname(null);}
		
		if(!userFormData.getPassword().equals(userFormData.getPasswordConfirm())) {
			model.addAttribute("errorMessage", "Passwords don't match.");
			return "register-failure";
		}
		
		if(!userFormData.getUserId().toLowerCase().matches("[\\w\\/-]+")) {
			log.error("Registering user " + userFormData.getUserId() + " failed. Username contains invalid characters");
			//This check is important as this regex is used to detect mentions in application code
			model.addAttribute("errorMessage", "Username contains invalid characters");
			return "register-failure";
		}

		//Prevent user ID's that are less that 2 characters long
		if(userFormData.getUserId().length() < 2) {
			log.error("Registering user " + userFormData.getUserId() + " failed. Username must be at least 2 characters long");
			model.addAttribute("errorMessage", "Username must be at least 2 characters long");
			return "register-failure";
		}

		if(userRepo.getUserByEmail(userFormData.getEmail()) != null) {
			log.error("Registering user " + userFormData.getUserId() + " failed. The email " + userFormData.getEmail() + " is already being used by another account.");
			model.addAttribute("errorMessage", "This email address is already being used by another account");
			return "register-failure";			
		}

		OlzUser existingUser = userRepo.getUser(userFormData.getUserId());
		if(existingUser != null) {
			log.error("Registering user " + userFormData.getUserId() + " failed. Username is already registered.");
			model.addAttribute("errorMessage", "That username is already registered");
			return "register-failure";
		}

		log.info("Registering user " + userFormData.getUserId() + ". Basic validation checks passed");
		
		userFormData = userFormData.escapeFields();
		OlzUser user = userFormData.convertToUser();		
		userRepo.createUser(user);
		userRepo.createUserRole(user.getUserId());
		String password = encryptPassword(userFormData.getPassword());
		userRepo.setPassword(user.getUserId(), password);
		
		log.info("Registering user " + userFormData.getUserId() + ". Created new user account successfully");
		
		model.addAttribute("user", user);

		return "register-success";
	}
	
	private String encryptPassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		password = encoder.encode(password);
		return password;
	}

}