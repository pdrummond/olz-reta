package iode.olz.reta.service;

import iode.olz.reta.messages.OlzMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultBroadcastMessageService implements BroadcastMessageService {

	@Autowired
	protected SimpMessagingTemplate template;
	
	public void sendMessage(OlzMessage message) {
		this.template.convertAndSend("/topic/messages", message);
	}
}
