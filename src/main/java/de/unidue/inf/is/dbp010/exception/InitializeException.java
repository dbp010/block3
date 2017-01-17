package de.unidue.inf.is.dbp010.exception;

public class InitializeException extends Exception {

	private static final long serialVersionUID = 1L;

	public InitializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializeException(String message) {
		super(message);
	}

}