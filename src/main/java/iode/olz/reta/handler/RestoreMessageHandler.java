package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RestoreMessageHandler extends AbstractCreateMessageHandler {
	
    @MessageMapping("/restore-message")
    public OlzResult restoreMessage(OlzMessage message, Principal principal) throws Exception {
    	doCreateMessage(OlzMessageType.RESTORE_MESSAGE, message, principal);
    	updateReferredMessage(message.copyWithNewReferrredMessage(message.getReferredMessage().copyWithNewArchived(false)), principal);
        return success();
    }
}