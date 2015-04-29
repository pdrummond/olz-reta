package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ArchiveMessageHandler extends AbstractCreateMessageHandler {
	
    @MessageMapping("/archive-message")
    public OlzResult archiveMessage(OlzMessage message, Principal principal) throws Exception {
    	doCreateMessage(OlzMessageType.ARCHIVE_MESSAGE, message, principal);
    	updateReferredMessage(message.copyWithNewReferrredMessage(message.getReferredMessage().copyWithNewArchived(true)), principal);
        return success();
    }
}