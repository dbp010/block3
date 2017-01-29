package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class EpisodeServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public EpisodeServlet() {
		super("episode.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		Episode		episode		=	(Episode)	loadEntity(req, "eid", Entity.Episode, pm);
		
		List<Object>	figures		=	null;
		List<Object>	locations	=	null;
		
		if(episode != null){
			
			try {
				figures		=	pm.loadFiguresByEid(episode.getEid());
			} catch (PersistenceManagerException e) {
				throw new IOException("Load figures by eid: " + episode.getEid() + " failed", e);
			}
			
			try {
				locations	=	pm.loadLocationsByEid(episode.getEid());
			} catch (PersistenceManagerException e) {
				throw new IOException("Load locations by eid: " + episode.getEid() + " failed", e);
			}
			
			addRatingAttributes(RatingType.Episode, episode.getEid(), pm, req, resp);
		}
		
		req.setAttribute("episode", 	episode);
		req.setAttribute("locations", 	locations);
		req.setAttribute("figures", 	figures);
		
	}
}