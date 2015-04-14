package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class HashTag implements Comparable<HashTag> {
	private final String id;
	private final String loopItemId;
	private final String tag;
	private final String tagName;
	private final Long longValue;
	private final Double doubleValue;
	private final String textValue;
	private final String color;
	private final HashTagType hashTagType;
	private final HashTagValueType valueType;
	private final String createdBy;
	private final Long createdAt;
	private final String updatedBy;
	private final Long updatedAt;

	@JsonCreator
	public HashTag(
			@JsonProperty("id") String id, 
			@JsonProperty("loopItemId") String loopItemId, 
			@JsonProperty("tag") String tag, 
			@JsonProperty("tagName") String tagName, 
			@JsonProperty("longValue") Long longValue,
			@JsonProperty("doubleValue") Double doubleValue,
			@JsonProperty("textValue") String textValue,
			@JsonProperty("color") String color,
			@JsonProperty("hashTagType") HashTagType hashTagType,
			@JsonProperty("valueType") HashTagValueType valueType,
			@JsonProperty("createdAt") Long createdAt, 
			@JsonProperty("createdBy") String createdBy,
			@JsonProperty("updatedAt") Long updatedAt, 
			@JsonProperty("updatedBy") String updatedBy) {

		this.id = id;
		this.loopItemId = loopItemId;
		this.tag = tag;
		this.tagName = tagName; 
		this.longValue = longValue;
		this.doubleValue = doubleValue;
		this.textValue = textValue;
		this.color = color;
		this.hashTagType = hashTagType;
		this.valueType = valueType;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;		
	}
	
	public String getId() {
		return id;
	}

	public String getLoopItemId() {
		return loopItemId;
	}

	public String getTag() {
		return tag;
	}

	public String getTagName() {
		return tagName;
	}
	
	public Long getLongValue() {
		return longValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}
	
	public String getTextValue() {
		return textValue;
	}
	
	public String getColor() {
		return color;
	}
	
	public HashTagType getHashTagType() {
		return hashTagType;
	}

	public HashTagValueType getValueType() {
		return valueType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int compareTo(HashTag hashtag) {
		return Long.valueOf(getUpdatedAt()).compareTo(Long.valueOf(hashtag.getUpdatedAt()));
	}
	
	public static class Builder {
		private String id;
		private String loopItemId;
		private String tag;
		private String tagName;
		private Long longValue;
		private Double doubleValue;
		private String textValue;
		private String color;
		private HashTagType hashTagType = HashTagType.NORMAL;
		private HashTagValueType valueType = HashTagValueType.NONE;
		private String createdBy;
		private Long createdAt;
		private String updatedBy;
		private Long updatedAt;
		
		public Builder id(String val) 					{ this.id = val; return this;}
		public Builder loopItemId(String val) 			{ this.loopItemId = val; return this;}
		public Builder tag(String val) 					{ this.tag = val; return this;}
		public Builder tagName(String val) 				{ this.tagName = val; return this;}
		public Builder longValue(Long val) 				{ this.longValue = val; return this;}
		public Builder doubleValue(Double val) 			{ this.doubleValue = val; return this;}
		public Builder textValue(String val) 			{ this.textValue = val; return this;}
		public Builder color(String val) 				{ this.color = val; return this;}
		public Builder hashTagType(HashTagType val) 	{ this.hashTagType = val; return this;}
		public Builder valueType(HashTagValueType val) 	{ this.valueType = val; return this;}
		public Builder createdBy(String val) 			{ this.createdBy = val; return this;}
		public Builder updatedBy(String val) 			{ this.updatedBy = val; return this;}
		public Builder createdAt(long val) 				{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 				{ this.updatedAt = val; return this;}

		public HashTag build() {
			return new HashTag(this);
		}
	}
	
	protected HashTag(Builder b) {
		this.id = b.id;
		this.loopItemId = b.loopItemId;
		this.tag = b.tag;
		this.tagName = b.tagName;
		this.longValue = b.longValue;
		this.doubleValue = b.doubleValue;
		this.textValue = b.textValue;
		this.color = b.color;
		this.hashTagType = b.hashTagType;
		this.valueType = b.valueType;
		this.createdBy = b.createdBy;
		this.updatedBy = b.updatedBy;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
	}
}
