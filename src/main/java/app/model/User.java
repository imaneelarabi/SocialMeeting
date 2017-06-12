package app.model;

import org.springframework.data.annotation.Id;


public class User {
	@Id
	private String email;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String shortbiography;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getShortbiography() {
		return shortbiography;
	}
	public void setShortbiography(String shortbiography) {
		this.shortbiography = shortbiography;
	}
	
}
