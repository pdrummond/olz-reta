package iode.olz.reta.handler;

import iode.olz.reta.dao.HashTag;
import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.dao.ParsedTags;
import iode.olz.reta.messages.NewLoopMessage;
import iode.olz.reta.repo.HashTagRepository;
import iode.olz.reta.repo.LoopItemRepository;
import iode.olz.reta.result.OlzResult;
import iode.olz.reta.service.BroadcastMessageService;
import iode.olz.reta.service.TagParserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewLoopMessageHandler extends AbstractMessageHandler {
	
	@Autowired
	BroadcastMessageService broadcastMessageService;
		
	@Autowired
	TagParserService tagParserService;

	@Autowired
	LoopItemRepository loopItemRepo;
	
	@Autowired
	HashTagRepository hashTagRepo;


    @MessageMapping("/new_loop")    
    public OlzResult onNewLoopMessage(NewLoopMessage message) throws Exception {
    	
    	validateMessage(message);
    	LoopItem loopItem = persistNewLoop(message);
        broadcastMessage(message);        
        extractAndPersistHashTags(loopItem);
        return OlzResult.success();
    }

	private void validateMessage(NewLoopMessage message) {		
	}

	private LoopItem persistNewLoop(NewLoopMessage message) {
		return loopItemRepo.createLoopItem(new LoopItem.Builder().content(message.getContent()).build());
	} 
	
	private void broadcastMessage(NewLoopMessage message) {
		broadcastMessageService.sendMessage(message);
	}
	
	private void extractAndPersistHashTags(LoopItem loopItem) {
		ParsedTags parsedTags = tagParserService.parseLoopItem(loopItem);
		for(HashTag hashTag : parsedTags.getHashTags()) {
			hashTagRepo.createHashTag(hashTag);
		}
	}
	
}