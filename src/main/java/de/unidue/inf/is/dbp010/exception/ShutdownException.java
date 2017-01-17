package de.unidue.inf.is.dbp010.exception;

public class ShutdownException extends Exception {

	private static final long serialVersionUID = 1L;

	public ShutdownException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShutdownException(String message) {
		super(message);
	}

}
