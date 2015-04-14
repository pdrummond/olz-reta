package iode.olz.reta.service;

import iode.olz.reta.messages.OlzMessage;

public interface BroadcastMessageService {

	public void sendMessage(OlzMessage message);
}
