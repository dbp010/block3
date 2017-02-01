package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

/**
 * Das ist die Eintrittsseite.
 */
public class GoTServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;
	
	public GoTServlet() {
		super("got.ftl");
	}
	
	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {

		List<Object> figures 	= null;
		List<Object> houses		= null;
		List<Object> seasons	= null;
		List<Object> playlists	= null;
		
		try {
			figures		=	pm.loadEntities(Entity.figure, 5);
			houses 		=	pm.loadEntities(Entity.house, 5);
			seasons		=	pm.loadEntities(Entity.season, 5);
			playlists	=	pm.loadPlaylistsForUser(getUser(req, resp).getUsid());
		} catch (PersistenceManagerException e) {
			new PersistenceManagerException("Load summary entities failed", e).printStackTrace();
		}
			
		req.setAttribute("figures", 	figures);
		req.setAttribute("seasons", 	seasons);
		req.setAttribute("houses", 		houses);
		req.setAttribute("playlists", 	playlists);
	}
}