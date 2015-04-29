package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
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
public class ArchiveMessageHandler extends AbstractCreateMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
		
	@Autowired
	TagParserService tagParserService;

	@Autowired
	OlzMessageRepository messageRepo;
	
	@Autowired
	HashTagRepository hashTagRepo;
	
	@Autowired UpdateMessageEventHandler updateMessageEventHandler;

    @MessageMapping("/archive-message")
    public OlzResult archiveMessage(OlzMessage message, Principal principal) throws Exception {
    	doCreateMessage(OlzMessageType.ARCHIVE_MESSAGE, message, principal);
    	updateReferredMessage(message.copyWithNewReferrredMessage(message.getReferredMessage().copyWithNewArchived(true)), principal);
        return success();
    }
}