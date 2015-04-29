package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.dao.UserTag;
import iode.olz.reta.repo.OlzMessageRepository;
import iode.olz.reta.service.BroadcastMessageService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AbstractCreateMessageHandler extends AbstractMessageHandler {
	
	@Autowired
	protected BroadcastMessageService broadcastMessageService;
		
	@Autowired
	protected OlzMessageRepository messageRepo;
		
	@Autowired UpdateMessageEventHandler updateMessageEventHandler;

    protected void doCreateMessage(OlzMessageType messageType, OlzMessage message, Principal principal) {
    	message = fillMessage(messageType, message, principal);
    	message = validateMessage(message);    	
    	persistMessage(message);
    	broadcastMessage(message);     	
    }

	private OlzMessage fillMessage(OlzMessageType messageType, OlzMessage message, Principal principal) {
		UserTag curUserTag = getUserTagFromPrincipal(principal);
		return new OlzMessage.Builder(message)
				.messageType(messageType)
				.createdBy(curUserTag)
				.updatedBy(curUserTag)
				.build();
	}

	private OlzMessage validateMessage(OlzMessage message) {
		return message;
	}

	private OlzMessage persistMessage(OlzMessage message) {
		return messageRepo.createMessage(message);
	} 
	
	protected OlzMessage updateReferredMessage(OlzMessage message, Principal principal) {		
		updateMessageEventHandler.createUpdateMessageEvent(message, principal);
		return message;
	}
	
	private void broadcastMessage(OlzMessage message) {
		broadcastMessageService.sendMessage(message);
	}	
}