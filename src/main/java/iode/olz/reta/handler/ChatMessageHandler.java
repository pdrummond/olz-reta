package iode.olz.reta.handler;

import iode.olz.reta.dao.HashTag;
import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.dao.ParsedTags;
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
public class ChatMessageHandler extends AbstractMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
		
	@Autowired
	TagParserService tagParserService;

	@Autowired
	OlzMessageRepository messageRepo;
	
	@Autowired
	HashTagRepository hashTagRepo;


    @MessageMapping("/chat-message")
    public OlzResult onChatMessage(OlzMessage message, Principal principal) throws Exception {
    	message = fillMessage(message, principal);
    	message = validateMessage(message);    	
    	persistMessage(message);
        broadcastMessage(message);        
        extractAndPersistHashTags(message);
        return success();
    }

	private OlzMessage fillMessage(OlzMessage message, Principal principal) {
		UserTag curUserTag = getUserTagFromPrincipal(principal);
		return new OlzMessage.Builder(message)
				.messageType(OlzMessageType.CHAT_MESSAGE)
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
	
	private void broadcastMessage(OlzMessage message) {
		broadcastMessageService.sendMessage(message);
	}
	
	private void extractAndPersistHashTags(OlzMessage message) {
		ParsedTags parsedTags = tagParserService.parseLoopItem(message);
		for(HashTag hashTag : parsedTags.getHashTags()) {
			hashTagRepo.createHashTag(hashTag);
		}
	}
	
}