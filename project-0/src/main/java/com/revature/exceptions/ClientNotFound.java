package com.revature.exceptions;

public class ClientNotFound extends Exception{
	
	public ClientNotFound() {
		super();
	}

	public ClientNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotFound(String message) {
		super(message);
	}

	public ClientNotFound(Throwable cause) {
		super(cause);
	}
	
	


}
