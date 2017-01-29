package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public abstract class AGoTServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected Object loadEntity(HttpServletRequest req, String idParamName, Entity type, GOTDB2PersistenceManager pm) throws IOException {
		
		String idParamValue = String.valueOf(req.getParameter(idParamName));
		
		Object entityObject = null;
		
		if(idParamValue != null && (idParamValue = idParamValue.trim()).length() > 0) {
			long id;

			try {
				
				id = Long.valueOf(idParamValue);
				return loadEntity(id, type, pm);
				
			}catch(NumberFormatException e){
				throw new IOException("Parsing id parameter {name:" + idParamName + " value: " + idParamValue + "} as number failed", e);
			}
		}
		
		return entityObject;
	}
	
	protected List<Object> loadEntities(Entity type, GOTDB2PersistenceManager pm) throws IOException {
		try {
			return pm.loadEntities(type);
		} catch (PersistenceManagerException e) {
			throw new IOException("Load entity objects of type: " + type + " failed", e);
		}
	}
	
	protected Object loadEntity(long id, Entity type, GOTDB2PersistenceManager pm) throws IOException {
		try {
			return pm.loadEntity(id, type);
		} catch (PersistenceManagerException e) {
			throw new IOException("Load entity object of type: " + type + "  with id: " + id + " failed", e);
		}
	}
	
	protected GOTDB2PersistenceManager connect() throws IOException {
		GOTDB2PersistenceManager pm = new GOTDB2PersistenceManager();
		try {
			pm.connect();
		} catch (PersistenceManagerException e) {
			throw new IOException(e);
		}
		return pm;
	}

	protected void disconnect(GOTDB2PersistenceManager pm) throws IOException {
		if(pm == null)	
			return;

		try {
			pm.disconnect();
		} catch (PersistenceManagerException e) {
			throw new IOException();
		}
	}
	
}
