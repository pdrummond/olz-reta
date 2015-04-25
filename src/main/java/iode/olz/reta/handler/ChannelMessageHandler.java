package iode.olz.reta.handler;

import iode.olz.reta.dao.Channel;
import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.dao.UserTag;
import iode.olz.reta.repo.ChannelRepository;
import iode.olz.reta.repo.HashTagRepository;
import iode.olz.reta.repo.OlzMessageRepository;
import iode.olz.reta.result.OlzResult;
import iode.olz.reta.service.BroadcastMessageService;
import iode.olz.reta.service.TagParserService;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChannelMessageHandler extends AbstractMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
		
	@Autowired
	TagParserService tagParserService;

	@Autowired
	OlzMessageRepository messageRepo;
	
	@Autowired
	ChannelRepository channelRepo;
	
	@Autowired
	HashTagRepository hashTagRepo;


    @MessageMapping("/channel")
    public OlzResult onChannelMessage(OlzMessage message, Principal principal) throws Exception {
    	message = fillMessage(message, principal);
    	message = validateMessage(message);
    	persistMessage(message);   
    	createChannel(message);
        broadcastMessage(message);                
        return success();
    }

	private OlzMessage fillMessage(OlzMessage message, Principal principal) {
		UserTag curUserTag = getUserTagFromPrincipal(principal);
		return new OlzMessage.Builder(message)
				.id(message.getId()==null?UUID.randomUUID().toString():message.getId())
				.messageType(OlzMessageType.CHANNEL)
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
	
	private void createChannel(OlzMessage message) {
		channelRepo.createChannel(new Channel(
				null, 
				message.getId(),
				message.getTitle(),
				message.getContent(),				
				null,
				message.getCreatedBy(),
				null,
				message.getUpdatedBy()));
	}
	
	private void broadcastMessage(OlzMessage message) {
		broadcastMessageService.sendMessage(message);
	}
}