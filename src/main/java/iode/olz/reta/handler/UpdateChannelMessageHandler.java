package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UpdateChannelMessageHandler extends AbstractCreateMessageHandler {
	
    @MessageMapping("/update-channel")
    public OlzResult updateStatus(OlzMessage message, Principal principal) throws Exception {
    	doCreateMessage(OlzMessageType.UPDATE_CHANNEL, message, principal);
    	updateReferredMessage(message, principal);
        return success();
    }
}