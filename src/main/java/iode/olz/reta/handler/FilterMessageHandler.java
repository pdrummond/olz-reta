package iode.olz.reta.handler;

import iode.olz.reta.dao.HashTag;
import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.dao.ParsedTags;
import iode.olz.reta.dao.UserTag;
import iode.olz.reta.messages.NewLoopMessage;
import iode.olz.reta.repo.HashTagRepository;
import iode.olz.reta.repo.LoopItemRepository;
import iode.olz.reta.result.OlzResult;
import iode.olz.reta.service.BroadcastMessageService;
import iode.olz.reta.service.TagParserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FilterMessageHandler extends AbstractMessageHandler {
	
    @MessageMapping("/filter_message")    
    public OlzResult onFilterMessage(NewLoopMessage message, Principal principal) throws Exception {
    	
        return OlzResult.success();
    }	
}