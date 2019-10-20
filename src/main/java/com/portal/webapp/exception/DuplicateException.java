package com.portal.webapp.exception;

public class DuplicateException  extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public DuplicateException()
	{
		super();
	}
	
	public DuplicateException(String message)
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
