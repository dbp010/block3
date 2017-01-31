package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class HousesServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public HousesServlet() {
		super("houses.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		List<Object>	houses		=	null;
		
		String search = req.getParameter("sq");
		
		if(search == null || (search = search.trim()).isEmpty()){
			houses = loadEntities(Entity.House, pm);
		}
		else{
			
			try {
				houses = pm.searchHousesByName(search);
			} catch (PersistenceManagerException e) {
				throw new IOException("Search houses by name: " + search + " failed", e);
			}
			
			req.setAttribute("sq", search);
		}
		
		req.setAttribute("houses", houses);
		
		
	}
}