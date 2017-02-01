package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class SeasonsServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public SeasonsServlet() {
		super("seasons.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		List<Object>	seasons		=	null;
		
		String search = req.getParameter("sq");
		
		if(search == null || (search = search.trim()).isEmpty()){
			seasons = loadEntities(Entity.season, pm);
		}
		else {
			
			try {
				seasons = pm.searchSeasonsByEpisodeTitle(search);
			} catch (PersistenceManagerException e) {
				throw new IOException("Search seasons by episode title: " + search + " failed", e);
			}
			
			req.setAttribute("sq", search);
		}
		
		req.setAttribute("seasons", seasons);
		
	}
}