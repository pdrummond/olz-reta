package iode.olz.reta.repo;

import java.util.Date;
import java.util.List;

import iode.olz.reta.dao.OlzMessage;

public interface OlzMessageRepository {
	List<OlzMessage> getPageOfMessages(Date fromDate);
	OlzMessage getMessage(String id);
	OlzMessage createMessage(OlzMessage message);	
}