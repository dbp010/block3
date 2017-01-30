package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class PlaylistServlet extends AGoTServlet {

	public PlaylistServlet() {
		super("playlist.ftl");
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		
		Playlist	playlist	= 	(Playlist)	loadEntity(req, "plid", Entity.Playlist, pm);
		
		List<Object>	episodes	=	null;
		
		if(playlist != null){
			
			try {
				episodes	=	pm.loadEpisodesForPlaylist(playlist.getPlid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load episodes for playlist: " + playlist + " failed", e);
			}
			
		}
		
		req.setAttribute("playlist", 	playlist);
		req.setAttribute("episodes", 	episodes);
	}

}
