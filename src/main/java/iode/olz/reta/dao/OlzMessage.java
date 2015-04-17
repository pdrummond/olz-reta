package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OlzMessage {
	private String id;
	private OlzMessageType messageType;
	private final UserTag createdBy;
	private final long createdAt;
	private final UserTag updatedBy;
	private final long updatedAt;
	private final String content;
	private final Boolean archived;
	
	public OlzMessage(
			@JsonProperty("id") String id,
			@JsonProperty("itemType") OlzMessageType messageType,
			@JsonProperty("content") String content,
			@JsonProperty("archived") Boolean archived,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") UserTag createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") UserTag updatedBy) {
		this.id = id;
		this.messageType = messageType;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;		
		this.content = content;		
		this.archived = archived;
	}
	
	public String getId() {
		return id;
	}
	
	public OlzMessageType getMessageType() {
		return messageType;
	}
	
	public long getCreatedAt() {
		return createdAt;
	}

	public UserTag getCreatedBy() {
		return createdBy;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public UserTag getUpdatedBy() {
		return updatedBy;
	}
	
	public String getContent() {
		return content;
	}

	public Boolean isArchived() {
		return archived==null?Boolean.FALSE:archived;
	}
	
	public static class Builder {
		private String id;
		private OlzMessageType messageType = OlzMessageType.CHAT_MESSAGE;
		private UserTag createdBy;
		private long createdAt;
		private UserTag updatedBy;
		private long updatedAt;
		private String content;
		private Boolean archived;
		
		public Builder() {
			
		}
		
		public Builder(OlzMessage m) {
			this.id = m.id;
			this.messageType = m.messageType;
			this.createdBy = m.createdBy;
			this.createdAt = m.createdAt;
			this.updatedBy = m.updatedBy;
			this.updatedAt = m.updatedAt;
			this.content = m.content;
			this.archived = m.archived;
		}
		
		public Builder id(String val) 					{ this.id = val; return this;}
		public Builder messageType(OlzMessageType val) 	{ this.messageType = val; return this;}
		public Builder createdBy(UserTag val) 			{ this.createdBy = val; return this;}
		public Builder updatedBy(UserTag val) 			{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 				{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 				{ this.updatedAt = val; return this;}
		public Builder content(String val) 				{ this.content = val; return this;}
		public Builder archived(Boolean val)			{ this.archived = val; return this;}
		
		public OlzMessage build() {
			return new OlzMessage(this);
		}
	}
	
	private OlzMessage(Builder b) {
		this.id = b.id;
		this.messageType = b.messageType;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
		this.content = b.content;
		this.archived = b.archived;
	}
}