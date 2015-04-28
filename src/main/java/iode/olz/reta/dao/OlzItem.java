package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OlzItem {
	private String id;
	private OlzItemType itemType;
	private final String title;
	private final String content;
	private final long status;
	private final UserTag createdBy;
	private final long createdAt;
	private final UserTag updatedBy;
	private final long updatedAt;
	
	public OlzItem(
			@JsonProperty("id") String id,
			@JsonProperty("itemType") OlzItemType itemType,
			@JsonProperty("title") String title,
			@JsonProperty("content") String content,
			@JsonProperty("status") long status,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") UserTag createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") UserTag updatedBy) {
		this.id = id;		
		this.itemType = itemType;
		this.title = title;
		this.content = content;
		this.status = status;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;		
	}
	
	public String getId() {
		return id;
	}
	
	public OlzItemType getItemType() {
		return itemType;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public long getStatus() {
		return status;
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
		private OlzItemType itemType = OlzItemType.COMMENT;
		private String title;
		private String content;
		private long status;
		private UserTag createdBy;
		private long createdAt;
		private UserTag updatedBy;
		private long updatedAt;
		
		public Builder() {
			
		}
		
		public Builder(OlzItem m) {
			this.id = m.id;
			this.itemType = m.itemType;
			this.title = m.title;
			this.content = m.content;			
			this.status = m.status;
			this.createdBy = m.createdBy;
			this.createdAt = m.createdAt;
			this.updatedBy = m.updatedBy;
			this.updatedAt = m.updatedAt;
		}
		
		public Builder id(String val) 				{ this.id = val; return this;}
		public Builder itemType(OlzItemType val) 	{ this.itemType = val; return this;}
		public Builder title(String val) 			{ this.title = val; return this;}
		public Builder content(String val) 			{ this.content = val; return this;}
		public Builder status(long val)				{ this.status = val; return this;}
		public Builder createdBy(UserTag val) 		{ this.createdBy = val; return this;}
		public Builder updatedBy(UserTag val) 		{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 			{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 			{ this.updatedAt = val; return this;}
		
		public OlzItem build() {
			return new OlzItem(this);
		}
	}
	
	private OlzItem(Builder b) {
		this.id = b.id;
		this.itemType = b.itemType;
		this.title = b.title;
		this.content = b.content;
		this.status = b.status;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
	}
}
