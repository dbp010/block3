package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.Transaction;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class SaveServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public SaveServlet() {
		super("save.ftl");
	}

	@Override
	protected void appendAttributes(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		String	entityType	=	req.getParameter("entity_type");
		Object	entity		=	null;
		Entity	type		=	null;
		
		if(entityType != null && (entityType = entityType.trim()).length() > 0) {
			
			try{
				type	=	Entity.valueOf(entityType);	
			}catch (IllegalArgumentException e) {
				throw new ServletException("Unknown entity type: " + type, e);
			}
			
			if(type != null)
				entity = saveEntity(type, req, resp);
			
		}
		
		req.setAttribute("entity_type", 	type);
		req.setAttribute("entity", 			entity);
	}
	
	private Object saveEntity(Entity type, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object entity = null;
		
		switch (type) {
		case playlist:
			entity = savePlaylist(req, resp);
			break;

		default:
			throw new ServletException("Save entity for type: " + type + " not defined");
		}
		
		return entity;
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {}

	private Object savePlaylist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String	name	=	req.getParameter("name");
		
		Playlist playlist = new Playlist();
		
		playlist.setName(name);
		playlist.setUser(getUser(req, resp));
		
		saveEntity(playlist, Entity.playlist);
		
		return playlist;
	}

	private void saveEntity(Object entity, Entity type) throws IOException {
		GOTDB2PersistenceManager pm = new GOTDB2PersistenceManager();
		
		try {
			Transaction t = pm.beginTransaction();
			t.saveEntity(entity, type);
			t.commit(true);
		} catch (PersistenceManagerException e){
			throw new IOException("Save entity: " + entity + " failed", e);
		}
	}

}
