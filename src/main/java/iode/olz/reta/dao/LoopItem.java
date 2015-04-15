package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoopItem  {
	private String id;
	private OlzItemType itemType;
	private final UserTag createdBy;
	private final long createdAt;
	private final UserTag updatedBy;
	private final long updatedAt;
	private final String content;
	private final Boolean archived;
	
	public LoopItem(
			@JsonProperty("id") String id,
			@JsonProperty("itemType") OlzItemType itemType,
			@JsonProperty("content") String content,
			@JsonProperty("archived") Boolean archived,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") UserTag createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") UserTag updatedBy) {
		this.id = id;
		this.itemType = itemType;
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
	
	public OlzItemType getItemType() {
		return itemType;
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
		private OlzItemType itemType = OlzItemType.LOOP_ITEM;
		private UserTag createdBy;
		private long createdAt;
		private UserTag updatedBy;
		private long updatedAt;
		private String content;
		private Boolean archived;
		
		public Builder id(String val) 				{ this.id = val; return this;}
		public Builder itemType(OlzItemType val) 	{ this.itemType = val; return this;}
		public Builder createdBy(UserTag val) 		{ this.createdBy = val; return this;}
		public Builder updatedBy(UserTag val) 		{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 			{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 			{ this.updatedAt = val; return this;}
		public Builder content(String val) 			{ this.content = val; return this;}
		public Builder archived(Boolean val)		{ this.archived = val; return this;}
		
		public LoopItem build() {
			return new LoopItem(this);
		}
	}
	
	private LoopItem(Builder b) {
		this.id = b.id;
		this.itemType = b.itemType;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
		this.content = b.content;
		this.archived = b.archived;
	}
}
