package iode.olz.reta.repo;

import java.util.Date;
import java.util.List;

import iode.olz.reta.dao.OlzMessage;

public interface OlzMessageRepository {
	List<OlzMessage> getChannels(Date fromDate);
	List<OlzMessage> getPageOfMessagesWithFilter(Date fromDate, String query);
	OlzMessage getMessage(String id);
	OlzMessage createMessage(OlzMessage message);
	boolean filterMessage(OlzMessage message);
	OlzMessage updateMessage(OlzMessage message);	
}