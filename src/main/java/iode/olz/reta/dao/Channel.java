package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Channel implements Comparable<Channel> {
	private final String id;
	private final String messageId;
	private final String title;
	private final String content;
	private final UserTag createdBy;
	private final Long createdAt;
	private final UserTag updatedBy;
	private final Long updatedAt;

	@JsonCreator
	public Channel(
			@JsonProperty("id") String id, 
			@JsonProperty("messageId") String messageId, 
			@JsonProperty("title") String title, 
			@JsonProperty("content") String content, 
			@JsonProperty("createdAt") Long createdAt, 
			@JsonProperty("createdBy") UserTag createdBy,
			@JsonProperty("updatedAt") Long updatedAt, 
			@JsonProperty("updatedBy") UserTag updatedBy) {

		this.id = id;
		this.messageId = messageId;
		this.title = title;		
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;		
	}
	
	public String getId() {
		return id;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
		
	public UserTag getCreatedBy() {
		return createdBy;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public UserTag getUpdatedBy() {
		return updatedBy;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int compareTo(Channel channel) {
		return Long.valueOf(getUpdatedAt()).compareTo(Long.valueOf(channel.getUpdatedAt()));
	}
}
