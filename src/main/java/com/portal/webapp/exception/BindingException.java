package com.portal.webapp.exception;

public class BindingException extends Exception {

	private static final long serialVersionUID = -1646083143194195402L;
	private String message;
	
	public BindingException()
	{
		super();
	}
	
	public BindingException(String message)
	{
		super(message);
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
