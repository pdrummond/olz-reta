package iode.olz.reta.service;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.handler.FilterMessageHandler;
import iode.olz.reta.repo.OlzMessageRepository;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultBroadcastMessageService implements BroadcastMessageService {

	@Autowired
	protected SimpMessagingTemplate template;

	@Autowired
	private OlzMessageRepository messageRepo;

	public void sendMessage(OlzMessage message) {
		if(StringUtils.isEmpty(FilterMessageHandler.filterQuery)) {
			doSendMessage(message);
		} else {
			if(messageRepo.filterMessage(message)) {
				doSendMessage(message);
			} else {
				doSendHiddenMessage(message);
			}
		}
	}

	private void doSendMessage(OlzMessage message) {
		this.template.convertAndSend("/topic/messages", message);		
	}
	
	private void doSendHiddenMessage(OlzMessage message) {
		this.template.convertAndSend("/topic/hidden-messages", message);		
	}
}
