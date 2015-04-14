package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class OlzUser {
	private String userId;	
	private String email; 	
	private String firstName; 	
	private String surname;
	private Long createdAt;
	private Long updatedAt;
	private Boolean enabled;
	
	@JsonCreator
	public OlzUser(
			@JsonProperty("userId") String userId, 
			@JsonProperty("email") String email, 
			@JsonProperty("firstName") String firstName, 
			@JsonProperty("surname") String surname, 		
			@JsonProperty("createdAt") Long createdAt, 
			@JsonProperty("updatedAt") Long updatedAt, 
			@JsonProperty("enabled") Boolean enabled) {
		this.userId = userId;		
		this.email = email;
		this.firstName = firstName;
		this.surname = surname;		
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	public Long getUpdatedAt() {
		return updatedAt;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public static class Builder {
		private String userId;	
		private String email; 	
		private String firstName; 	
		private String surname;
		private Long createdAt;
		private Long updatedAt;
		private Boolean enabled;
		
		public Builder userId(String val) 		{ this.userId = val; return this;}
		public Builder email(String val) 		{ this.email = val; return this;}
		public Builder firstName(String val)	{ this.firstName = val; return this;}
		public Builder surname(String val) 		{ this.surname= val; return this;}
		public Builder createdAt(long val) 		{ this.createdAt = val; return this;}
		public Builder updatedAt(long val) 		{ this.updatedAt = val; return this;}
		public Builder enabled(Boolean val) 	{ this.enabled = val; return this;}

		public OlzUser build() {
			return new OlzUser(this);
		}
	}
	
	protected OlzUser(Builder b) {
		this.userId = b.userId;
		this.email = b.email;
		this.firstName = b.firstName;
		this.surname = b.surname;
		this.createdAt = b.createdAt;
		this.updatedAt = b.updatedAt;
		this.enabled = b.enabled;
	}

}
