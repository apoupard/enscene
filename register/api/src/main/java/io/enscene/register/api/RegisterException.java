package io.enscene.register.api;

public class RegisterException extends Exception {

	public RegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
