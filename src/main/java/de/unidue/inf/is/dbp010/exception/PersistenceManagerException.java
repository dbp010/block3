package de.unidue.inf.is.dbp010.exception;

public class PersistenceManagerException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceManagerException(String message) {
		super(message);
	}
}
