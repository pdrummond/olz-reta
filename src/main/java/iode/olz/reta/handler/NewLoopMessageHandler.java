package iode.olz.reta.handler;

import iode.olz.reta.messages.NewLoopMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NewLoopMessageHandler extends AbstractMessageHandler {

    @MessageMapping("/new_loop")
    @SendTo("/topic/messages")
    public NewLoopMessage onNewLoopMessage(NewLoopMessage message) throws Exception {
    	
    	validateMessage();
    	parseMessageTags();
    	persistMessage();
    	
        return message; 
    }

	private void validateMessage() {		
	}

	private void parseMessageTags() {
	}

	private void persistMessage() {
	} 

}