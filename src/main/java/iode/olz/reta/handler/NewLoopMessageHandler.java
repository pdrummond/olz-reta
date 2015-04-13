package iode.olz.reta.handler;

import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.handler.broadcast.BroadcastMessageService;
import iode.olz.reta.messages.NewLoopMessage;
import iode.olz.reta.repo.LoopItemRepository;
import iode.olz.reta.result.OlzResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewLoopMessageHandler extends AbstractMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
	
	@Autowired
	LoopItemRepository loopItemRepo;

    @MessageMapping("/new_loop")    
    public OlzResult onNewLoopMessage(NewLoopMessage message) throws Exception {
    	
    	validateMessage(message);
    	parseLoopTags(message);
    	persistNewLoop(message);    	
        broadcastMessage(message);
        
        return OlzResult.success();
    }

	private void validateMessage(NewLoopMessage message) {		
	}

	private void parseLoopTags(NewLoopMessage message) {
	}

	private void persistNewLoop(NewLoopMessage message) {
		loopItemRepo.createLoopItem(new LoopItem.Builder().content(message.getContent()).build());
	} 
	
	private void broadcastMessage(NewLoopMessage message) {
		broadcastMessageService.sendMessage(message);
	}
	
}