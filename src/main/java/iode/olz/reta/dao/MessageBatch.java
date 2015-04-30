package iode.olz.reta.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class MessageBatch<T> {
	
	private final List<T> messages;
	private final Long showMoreDate;
	private final Boolean noMoreMessages;
	
	@JsonCreator
	public MessageBatch(
			@JsonProperty("messages") List<T> messages, 
			@JsonProperty("showMoreDate") Long showMoreDate,
			@JsonProperty("noMoreMessages") Boolean noMoreMessages) {
		this.messages = messages;
		this.showMoreDate = showMoreDate;
		this.noMoreMessages= noMoreMessages;
	}
	
	public List<T> getMessages() {
		return messages;
	}
	
	public Long getShowMoreDate() {
		return showMoreDate;
	}
	
	public Boolean isNoMoreMessages() {
		return noMoreMessages;
	}
}
