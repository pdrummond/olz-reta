package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FilterMessageHandler extends AbstractMessageHandler {
	
	public static String filterQuery; 	
	
    @MessageMapping("/filter-message")    
    public OlzResult onFilterMessage(OlzMessage message, Principal principal) throws Exception {    	
    	//TEMP: Just storing this is memory for now.
    	//When there is time, I need to store per-user filter history in a specific database table.
    	FilterMessageHandler.filterQuery = message.getContent();  
        return success();
    }	
}