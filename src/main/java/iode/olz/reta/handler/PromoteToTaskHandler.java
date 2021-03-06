package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.dao.UserTag;
import iode.olz.reta.repo.HashTagRepository;
import iode.olz.reta.repo.OlzMessageRepository;
import iode.olz.reta.result.OlzResult;
import iode.olz.reta.service.BroadcastMessageService;
import iode.olz.reta.service.TagParserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PromoteToTaskHandler extends AbstractMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
		
	@Autowired
	TagParserService tagParserService;

	@Autowired
	OlzMessageRepository messageRepo;
	
	@Autowired
	HashTagRepository hashTagRepo;
	
	@Autowired UpdateMessageEventHandler updateMessageEventHandler;


    @MessageMapping("/promote-to-task")
    public OlzResult onPromoteToTaskMessage(OlzMessage message, Principal principal) throws Exception {
    	message = fillMessage(message, principal);
    	message = validateMessage(message);    	
    	persistMessage(message);
    	broadcastMessage(message); 
    	updateReferredMessage(message, principal);
        return success();
    }

	private OlzMessage fillMessage(OlzMessage message, Principal principal) {
		UserTag curUserTag = getUserTagFromPrincipal(principal);
		return new OlzMessage.Builder(message)
				.messageType(OlzMessageType.PROMOTE_TO_TASK)
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
	
	private OlzMessage updateReferredMessage(OlzMessage message, Principal principal) {
		message = message.copyWithNewReferrredMessage(new OlzMessage.Builder(message.getReferredMessage()).messageType(OlzMessageType.TASK).build());
		updateMessageEventHandler.createUpdateMessageEvent(message, principal);
		return message;
	}
	
	private void broadcastMessage(OlzMessage message) {
		broadcastMessageService.sendMessage(message);
	}	
}