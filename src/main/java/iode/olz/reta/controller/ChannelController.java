package iode.olz.reta.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import iode.olz.reta.dao.Channel;
import iode.olz.reta.repo.ChannelRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/channels")
public class ChannelController {
	private final Logger log = Logger.getLogger(ChannelController.class);
	
	@Autowired
	ChannelRepository channelRepo;

	@RequestMapping(method=GET)
	public List<Channel> getPageOfChannels(@RequestParam(value="fromDate", required=false) Long from, Principal principal) {		
		if(log.isDebugEnabled()) {
			log.debug("> getPageOfChannels(from="+ from + ")");
		}
		
		Date fromDate = null;
		List<Channel> messages = channelRepo.getPageOfChannels(fromDate);

		if(log.isDebugEnabled()) {
			log.debug("< getPageOfChannels()");
		}
		return messages;
	}
}
