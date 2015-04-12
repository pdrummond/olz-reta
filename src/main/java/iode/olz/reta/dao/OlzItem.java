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
}
