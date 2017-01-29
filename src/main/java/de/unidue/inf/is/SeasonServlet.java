package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Season;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class SeasonServlet extends AGoTServlet {

	public SeasonServlet() {
		super("season.ftl");
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		
		Season	season 	= 	(Season)	loadEntity(req, "sid", Entity.Season, pm);
		
		List<Object>	episodes	=	null;
		
		if(season != null){
			
			try {
				episodes	=	pm.loadEpisodesBySid(season.getSid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load episodes by sid: " + season.getSid() + " failed", e);
			}
			
			addRatingAttributes(RatingType.Season, season.getSid(), pm, req, resp);
		}
		
		req.setAttribute("season", 		season);
		req.setAttribute("episodes", 	episodes);
	}

}
