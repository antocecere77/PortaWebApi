package com.portal.webapp.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -8729169303699924451L;
	
	private String message = "Item not found";

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
