package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Castle;
import de.unidue.inf.is.dbp010.db.entity.Location;
import de.unidue.inf.is.dbp010.db.util.Belonging;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class LocationServlet extends AGoTServlet {

	public LocationServlet() {
		super("location.ftl");
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		
		Location 	location 	= 	(Location)	loadEntity(req, "lid", Entity.Location, pm);
		
		Belonging		belonging	=	null;
		Castle			castle		=	null;
		
		List<Object>	persons		=	null;
		List<Object>	episodes	=	null;
		
		if(location != null){
			
			try {
				belonging	=	pm.loadActualBelongingByLid(location.getLid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load actual belonging by lid: " + location.getLid() + " failed", e);
			}
			
			try {
				castle	=	pm.loadCastleByLid(location.getLid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load castle by lid: " + location.getLid() + " failed", e);
			}
			
			try {
				persons	=	pm.loadPerosnsByBirthplace(location.getLid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load persons by birthplace: " + location.getLid() + " failed", e);
			}
			
			try {
				episodes	=	pm.loadEpisodesByLid(location.getLid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load episodes by lid: " + location.getLid() + " failed", e);
			}
		}
		
		req.setAttribute("location", 	location);
		req.setAttribute("belonging", 	belonging);
		req.setAttribute("castle", 		castle);
		req.setAttribute("persons", 	persons);
		req.setAttribute("episodes", 	episodes);
	}

}
