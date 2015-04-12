package iode.olz.reta.handler;

import iode.olz.reta.messages.NewLoopMessage;
import iode.olz.reta.result.OlzResult;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewLoopMessageHandler extends AbstractMessageHandler {

    @MessageMapping("/new_loop")    
    public OlzResult onNewLoopMessage(NewLoopMessage message) throws Exception {
    	
    	validateMessage();
    	parseMessageTags();
    	persistMessage();    	
        broadcastMessage(message);
        
        return OlzResult.success();
    }

	private void validateMessage() {		
	}

	private void parseMessageTags() {
	}

	private void persistMessage() {
	} 

}