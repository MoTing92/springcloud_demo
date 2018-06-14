package com.mvne.springcloud.common.util.model;


public class Recipient {

	private String recipienName;
	
	private String email;

	public Recipient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Recipient(String recipienName, String email) {
		super();
		this.setRecipienName(recipienName);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecipienName() {
		return recipienName;
	}

	public void setRecipienName(String recipienName) {
		this.recipienName = recipienName;
	}
	
	
}
