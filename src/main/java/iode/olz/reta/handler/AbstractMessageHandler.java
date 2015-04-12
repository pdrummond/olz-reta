package iode.olz.reta.handler;

import iode.olz.reta.messages.AbstractMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class AbstractMessageHandler {

	@Autowired
	protected SimpMessagingTemplate template;
	
	public void broadcastMessage(AbstractMessage message) {
		this.template.convertAndSend("/topic/messages", message);
	}
}
