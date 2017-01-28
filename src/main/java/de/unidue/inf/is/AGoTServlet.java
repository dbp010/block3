package de.unidue.inf.is;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public abstract class AGoTServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected Object loadEntity(HttpServletRequest req, String idParamName, Entity type) {
		
		String idParamValue = String.valueOf(req.getParameter(idParamName));
		
		Object entityObject = null;
		
		if(idParamValue != null && (idParamValue = idParamValue.trim()).length() > 0) {
			long id;
			try {
				 id = Long.valueOf(idParamValue);
				 
				 entityObject = GOTDB2PersistenceManager.getInstance().loadEntity(id, type);
				 
			}catch(NumberFormatException e){
				new Exception("Parsing id parameter {name:" + idParamName + " value: " + idParamValue + "} as number failed", e).printStackTrace();
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load entity object of type: " + type + "  with id: " + idParamValue + " failed", e).printStackTrace();;
			}
		}
		
		return entityObject;
	}

}
