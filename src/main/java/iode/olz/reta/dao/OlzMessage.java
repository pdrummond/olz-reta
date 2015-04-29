package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OlzMessage {
	private String id;
	private OlzMessageType messageType;
	private final String title;
	private final String content;
	private final Channel channel;
	private final boolean archived;
	private final long status;
	private final OlzMessage referredMessage;
	private final UserTag createdBy;
	private final long createdAt;
	private final UserTag updatedBy;
	private final long updatedAt;
	
	public OlzMessage(
			@JsonProperty("id") String id,
			@JsonProperty("itemType") OlzMessageType messageType,
			@JsonProperty("title") String title,
			@JsonProperty("content") String content,
			@JsonProperty("channel") Channel channel,
			@JsonProperty("archived") boolean archived,
			@JsonProperty("status") long status,
			@JsonProperty("referredMessage") OlzMessage referredMessage,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") UserTag createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") UserTag updatedBy) {
		this.id = id;		
		this.messageType = messageType;
		this.title = title;
		this.content = content;
		this.channel = channel;
		this.archived = archived;
		this.status = status;
		this.referredMessage = referredMessage;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;		
	}
	
	public String getId() {
		return id;
	}
	
	public OlzMessageType getMessageType() {
		return messageType;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public boolean isArchived() {
		return archived;
	}

	public long getStatus() {
		return status;
	}
	
	public OlzMessage getReferredMessage() {
		return referredMessage;
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
	
	public static class Builder {
		private String id;
		private OlzMessageType messageType = OlzMessageType.COMMENT;
		private String title;
		private String content;
		private Channel channel;
		private boolean archived;
		private long status;
		private OlzMessage referredMessage;
		private UserTag createdBy;
		private long createdAt;
		private UserTag updatedBy;
		private long updatedAt;
		
		public Builder() {
			
		}
		
		public Builder(OlzMessage m) {
			this.id = m.id;
			this.messageType = m.messageType;
			this.title = m.title;
			this.content = m.content;
			this.channel = m.channel;
			this.archived = m.archived;
			this.status = m.status;
			this.referredMessage = m.referredMessage;
			this.createdBy = m.createdBy;
			this.createdAt = m.createdAt;
			this.updatedBy = m.updatedBy;
			this.updatedAt = m.updatedAt;
		}
		
		public Builder id(String val) 					{ this.id = val; return this;}
		public Builder messageType(OlzMessageType val) 	{ this.messageType = val; return this;}
		public Builder title(String val) 				{ this.title = val; return this;}
		public Builder content(String val) 				{ this.content = val; return this;}
		public Builder channel(Channel val) 			{ this.channel = val; return this;}
		public Builder archived(boolean val)			{ this.archived = val; return this;}
		public Builder status(long val)					{ this.status = val; return this;}
		public Builder referredMessage(OlzMessage val)  { this.referredMessage = val; return this;}
		public Builder createdBy(UserTag val) 			{ this.createdBy = val; return this;}
		public Builder updatedBy(UserTag val) 			{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 				{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 				{ this.updatedAt = val; return this;}
		
		public OlzMessage build() {
			return new OlzMessage(this);
		}
	}
	
	private OlzMessage(Builder b) {
		this.id = b.id;
		this.messageType = b.messageType;
		this.title = b.title;
		this.content = b.content;
		this.channel = b.channel;
		this.archived = b.archived;
		this.status = b.status;
		this.referredMessage = b.referredMessage;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
	}
	
	@Override
	public String toString() {
		return "OlzMessage(type=" + this.messageType + ")";
	}

	public OlzMessage copyWithNewReferrredMessage(OlzMessage referredMessage) {
		return new OlzMessage.Builder(this).referredMessage(referredMessage).build();
	}

	public OlzMessage copyWithNewArchived(boolean archived) {
		return new OlzMessage.Builder(this).archived(archived).build();
	}
}
