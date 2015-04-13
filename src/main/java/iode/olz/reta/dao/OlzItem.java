package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An OlzItem is anything that can appear in the loop list including loops themselves 
 * as well as activity items and maybe even other types such as users.
 */
public class OlzItem {
	protected String id;
	protected OlzItemType itemType;
	protected final String createdBy;
	protected final long createdAt;
	protected final String updatedBy;
	protected final long updatedAt;
	
	@JsonCreator
	public OlzItem(
			@JsonProperty("id") String id, 
			@JsonProperty("itemType") OlzItemType itemType,
			@JsonProperty("createdAt") long createdAt, 
			@JsonProperty("createdBy") String createdBy,
			@JsonProperty("updatedAt") long updatedAt, 
			@JsonProperty("updatedBy") String updatedBy) {
		this.id = id;
		this.itemType = itemType;
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
	
	public long getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public static class Builder {
		private String id;
		private OlzItemType itemType = OlzItemType.LOOP_ITEM;
		private String createdBy;
		private long createdAt;
		private String updatedBy;
		private long updatedAt;
		
		public Builder id(String val) 				{ this.id = val; return this;}
		public Builder itemType(OlzItemType val) 	{ this.itemType = val; return this;}
		public Builder createdBy(String val) 		{ this.createdBy = val; return this;}
		public Builder updatedBy(String val) 		{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 			{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 			{ this.updatedAt = val; return this;}

		public OlzItem build() {
			return new OlzItem(this);
		}
	}
	
	protected OlzItem(Builder b) {
		this.id = b.id;
		this.itemType = b.itemType;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
	}

}
