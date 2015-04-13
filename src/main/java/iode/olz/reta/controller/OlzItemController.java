package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import iode.olz.reta.dao.OlzItem;
import iode.olz.reta.repo.OlzItemRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/olz-items")
public class OlzItemController {
	private final Logger log = Logger.getLogger(OlzItemController.class);
	
	@Autowired
	OlzItemRepository olzItemRepo;

	@RequestMapping(method=GET)
	public List<OlzItem> getOlzItems(@RequestParam(value="fromDate", required=false) Long from, Principal principal) {		
		if(log.isDebugEnabled()) {
			log.debug("> getOlzItems(from="+ from + ")");
		}
		
		Date fromDate = null;
		if(from != null ) {
			fromDate = new Date(from);
		}
		List<OlzItem> olzItems = olzItemRepo.getPageOfItems(fromDate);

		if(log.isDebugEnabled()) {
			log.debug("< getOlzItems()");
		}
		return olzItems;
	}
}
