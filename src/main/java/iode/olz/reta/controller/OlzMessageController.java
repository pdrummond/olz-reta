package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.handler.FilterMessageHandler;
import iode.olz.reta.repo.OlzMessageRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/messages")
public class OlzMessageController {
	private final Logger log = Logger.getLogger(OlzMessageController.class);
	
	@Autowired
	OlzMessageRepository messageRepo;

	@RequestMapping(method=GET)
	public List<OlzMessage> getPageOfMessages(@RequestParam(value="fromDate", required=false) Long from, Principal principal) {		
		if(log.isDebugEnabled()) {
			log.debug("> getPageOfMessages(from="+ from + ")");
		}
		
		Date fromDate = null;
		if(from != null ) {
			fromDate = new Date(from);
		}
		
		List<OlzMessage> messages = 
				StringUtils.isEmpty(FilterMessageHandler.filterQuery) 
				? messageRepo.getChannels(fromDate) 
				: messageRepo.getPageOfMessagesWithFilter(fromDate, FilterMessageHandler.filterQuery);

		if(log.isDebugEnabled()) {
			log.debug("< getPageOfMessages()");
		}
		return messages;
	}
}
