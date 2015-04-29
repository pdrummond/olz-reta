package iode.olz.reta.handler;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.OlzMessageType;
import iode.olz.reta.result.OlzResult;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UpdateStatusMessageHandler extends AbstractCreateMessageHandler {
	
    @MessageMapping("/update-status")
    public OlzResult updateStatus(OlzMessage message, Principal principal) throws Exception {
    	doCreateMessage(OlzMessageType.UPDATE_STATUS, message, principal);
    	updateReferredMessage(message, principal);
        return success();
    }
}