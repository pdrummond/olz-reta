package iode.olz.reta.service;

import iode.olz.reta.dao.OlzMessage;

public interface BroadcastMessageService {

	public void sendMessage(OlzMessage message);
}
