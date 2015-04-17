package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FilterMessageHandler extends AbstractMessageHandler {
	
    @MessageMapping("/filter-message")    
    public OlzResult onFilterMessage(OlzMessage message, Principal principal) throws Exception {    	
        return success();
    }	
}