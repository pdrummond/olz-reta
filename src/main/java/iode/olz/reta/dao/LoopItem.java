package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoopItem extends OlzItem {
	private final String content;
	private final Boolean archived;
	
	public LoopItem(
			@JsonProperty("id") String id,
			@JsonProperty("itemType") OlzItemType itemType,
			@JsonProperty("content") String content,
			@JsonProperty("archived") Boolean archived,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") String createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") String updatedBy) {
		super(id, itemType, createdAt, createdBy, updatedAt, updatedBy);
		this.content = content;		
		this.archived = archived;
	}
	
	public String getContent() {
		return content;
	}

	public Boolean isArchived() {
		return archived==null?Boolean.FALSE:archived;
	}
	
	public static class Builder extends OlzItem.Builder {
		private String content;
		private Boolean archived;
		public Builder content(String val) 		{ this.content = val; return this;}
		public Builder archived(Boolean val)	{ this.archived = val; return this;}
		
		public LoopItem build() {
			return new LoopItem(this);
		}
	}
	
	private LoopItem(Builder b) {
		super(b);
		this.content = b.content;
		this.archived = b.archived;
	}
}
