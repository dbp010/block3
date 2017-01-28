package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.utils.DBUtil;

/**
 * Das könnte die Eintrittsseite sein.
 */
public final class GoTServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String databaseToCheck = "mygot";
		boolean databaseExists = DBUtil.checkDatabaseExistsExternal(databaseToCheck);

		request.setAttribute("db2name", databaseToCheck);

		if (databaseExists) {
//			request.setAttribute("db2exists", "vorhanden! Supi!");
			System.out.println("Datenbank " + databaseToCheck + " vorhanden! Supi!");
		}
		else {
			request.setAttribute("db2exists", "nicht vorhanden :-(");
			System.out.println("Datenbank " + databaseToCheck + " nicht vorhanden :-(");
		}
		
		List<Object> figures 	= null;
		List<Object> houses		= null;
		List<Object> seasons	= null;
		List<Object> playlists	= null;
		
		try {
			
			figures		=	GOTDB2PersistenceManager.getInstance().loadEntities(Entity.Figure, 5);
			houses 		=	GOTDB2PersistenceManager.getInstance().loadEntities(Entity.House, 5);
			seasons		=	GOTDB2PersistenceManager.getInstance().loadEntities(Entity.Season, 5);
			playlists	=	GOTDB2PersistenceManager.getInstance().loadEntities(Entity.Playlist);
			
		} catch (PersistenceManagerException e) {
			new PersistenceManagerException("Load entities failed", e).printStackTrace();
		}
			
		request.setAttribute("figures", 	figures);
		request.setAttribute("seasons", 	seasons);
		request.setAttribute("houses", 		houses);
		request.setAttribute("playlists", 	playlists);
		
		request.getRequestDispatcher("got_start.ftl").forward(request, response);
	}

	
}
