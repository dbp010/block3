package de.unidue.inf.is.dbp010;

import de.unidue.inf.is.dbp010.exception.InitializeException;
import de.unidue.inf.is.dbp010.exception.ShutdownException;

public interface IInitializable {

	public void init() throws InitializeException;
	
	public void shutdown() throws ShutdownException;
}
