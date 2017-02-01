package de.unidue.inf.is;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Episode;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class PlaylistServlet extends AGoTServlet {

	public PlaylistServlet() {
		super("playlist.ftl");
	}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		
		Playlist	playlist	= 	(Playlist)	loadEntity(req, "plid", Entity.playlist, pm);
		
		List<Object>	episodes	=	null;
		
		if(playlist != null){
			
			try {
				episodes	=	pm.loadEpisodesForPlaylist(playlist.getPlid());
			} catch(PersistenceManagerException e){
				throw new IOException("Load episodes for playlist: " + playlist + " failed", e);
			}
			
			Collections.sort(episodes, new Comparator(){
				@Override
				public int compare(Object e1, Object e2) {
					return Integer.compare(
							((Episode)e1).getSeason().getNumber(),
							((Episode)e2).getSeason().getNumber());
				}
			});
			
		}
		
		req.setAttribute("playlist", 	playlist);
		req.setAttribute("episodes", 	episodes);
	}

}
