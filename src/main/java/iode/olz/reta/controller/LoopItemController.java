package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.repo.LoopItemRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/loop-items")
public class LoopItemController {
	private final Logger log = Logger.getLogger(LoopItemController.class);
	
	@Autowired
	LoopItemRepository loopItemRepo;

	@RequestMapping(method=GET)
	public List<LoopItem> getOlzItems(@RequestParam(value="fromDate", required=false) Long from, Principal principal) {		
		if(log.isDebugEnabled()) {
			log.debug("> getLoopItems(from="+ from + ")");
		}
		
		Date fromDate = null;
		if(from != null ) {
			fromDate = new Date(from);
		}
		List<LoopItem> olzItems = loopItemRepo.getPageOfLoopItems(fromDate);

		if(log.isDebugEnabled()) {
			log.debug("< getLoopItems()");
		}
		return olzItems;
	}
}
