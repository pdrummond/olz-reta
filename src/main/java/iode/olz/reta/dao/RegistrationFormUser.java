package iode.olz.reta.dao;

import org.apache.commons.lang.StringEscapeUtils;


public class RegistrationFormUser {
	//private final Logger log = Logger.getLogger(getClass());

	private String userId;
	private String password;
	private String passwordConfirm;
	private String email; 	
	private String firstName;
	private String surname;
	
	public RegistrationFormUser() {
		//Default constructor
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getPassword() {
		return password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	
	public String getEmail() {
		return email;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public RegistrationFormUser escapeFields() {
		this.setFirstName(StringEscapeUtils.escapeHtml(this.getFirstName()));
		this.setSurname(StringEscapeUtils.escapeHtml(this.getSurname()));
		return this;
	}

	public OlzUser convertToUser() {
		return new OlzUser.Builder()
		.userId(this.getUserId())
		.email(this.getEmail())
		.firstName(this.getFirstName())
		.surname(this.getSurname())
		.build();
	}
}
