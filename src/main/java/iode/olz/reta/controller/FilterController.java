package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import iode.olz.reta.handler.FilterMessageHandler;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/filter")
public class FilterController {
	
	@RequestMapping(method=GET)
	public String getFilter(Principal principal) {
		return FilterMessageHandler.filterQuery;
	}
}
