package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.Transaction;
import de.unidue.inf.is.dbp010.db.entity.Playlist;
import de.unidue.inf.is.dbp010.db.entity.Rating;
import de.unidue.inf.is.dbp010.db.util.RatingLink;
import de.unidue.inf.is.dbp010.db.util.RatingLink.RatingType;
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
			
			type = getEntityType(entityType);
			
			if(type != null)
				entity = saveEntity(type, req, resp);
			
		}
		
		req.setAttribute("entity_type", 	type);
		req.setAttribute("entity", 			entity);
	}
	
	private Entity getEntityType(String entityType) throws ServletException {
		try{
			return Entity.valueOf(entityType);	
		}catch (IllegalArgumentException e) {
			throw new ServletException("Unknown entity type: " + entityType, e);
		}
	}

	private Object saveEntity(Entity type, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object entity = null;
		
		switch (type) {
		case playlist:
			entity = savePlaylist(req, resp);
			break;
		case rating:
			entity = saveRating(req, resp);
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
		
		if(name == null || (name = name.trim()).length() == 0)
			throw new IOException("Save playlist without name failed");
		
		// First try to load existing playlist
		Playlist	playlist	=	null;
		
		GOTDB2PersistenceManager pm	=	connect();
		
		try {
			playlist	=	pm.loadUserPlaylistByName(getUser(req, resp), name);
		} catch (PersistenceManagerException e) {
			throw new IOException("Load user playlist by name failed", e);
		}
		finally {
			disconnect(pm);
		}
		
		// Playlist with given name already exists, return existing
		if(playlist != null)
			return playlist;
		
		// Playlist with given name not exists, create new one
		playlist = new Playlist();
		
		playlist.setName(name);
		playlist.setUser(getUser(req, resp));
		
		Transaction		transaction 	=	beginTransaction();
		
		long id;
		
		try {
			
			id = insertEntity(transaction, playlist, Entity.playlist);
			
			commit(transaction);
			
		} finally {
			close(transaction);
		}

		playlist.setPlid(id);
		
		return playlist;
	}

	private Object saveRating(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String	s_ratingType	=	req.getParameter("rating_type");
		String	s_ratingId		=	req.getParameter("rating_id");
		String	selectedRating	=	req.getParameter("selected_rating");
		String	text			=	req.getParameter("text");
		String	ratingEntityId	=	req.getParameter("rating_entity_id");
		
		Object	ratingEntity		=	null;
		
		long	ratingId			=	-1;
		int 	i_rating			=	-1;
		long	l_ratingEntityId	=	-1;
		
		try{
			ratingId	=	Long.valueOf(s_ratingId);
		}catch (NumberFormatException e) {
			throw new IOException("Rating id: " + s_ratingId + " is not a number", e);
		}
		
		try{
			i_rating	=	Integer.valueOf(selectedRating);
		}catch (NumberFormatException e) {
			throw new IOException("Rating: " + selectedRating + " is not a number", e);
		}
		
		try{
			l_ratingEntityId	=	Long.valueOf(ratingEntityId);
		}catch (NumberFormatException e) {
			throw new IOException("Rating entity id: " + ratingEntityId + " is not a number", e);
		}
		
		if(text == null)
			text = "";
		else
			text = text.trim();
		
		Rating		rating	= 	new Rating();
		
		rating.setRid(ratingId);
		rating.setUser(getUser(req, resp));
		rating.setRating(i_rating);
		rating.setText(text);

		RatingType ratingType		= 	getRatingType(s_ratingType);
		
		ratingEntity				=	loadRatingEntity(ratingType, l_ratingEntityId);

		RatingLink	ratingLink	=	new RatingLink();
		
		ratingLink.setRating(rating);
		ratingLink.setType(ratingType);
		ratingLink.setRatingEntity(ratingEntity);

		
		Transaction		transaction		=	beginTransaction();
		
		try {
			
			if(ratingId == -1) {
				ratingId = insertEntity(transaction, rating, 		Entity.rating);
				
				rating.setRid(ratingId);

				insertEntity(transaction, ratingLink, 	Entity.ratinglink);
			}
			else {
				
				GOTDB2PersistenceManager pm	=	connect();
				Rating	existingRating		=	null;
				
				try { 
					
					existingRating	=	(Rating)	loadEntity(rating.getRid(), Entity.rating, pm);
					
				}
				finally {
					disconnect(pm);
				}
				
				if(!rating.equals(existingRating)){
					updateEntity(transaction, rating, Entity.rating);
				}
				
			}
			
			commit(transaction);
			
		} finally {
			close(transaction);
		}
		
		return ratingLink;
	}
	
	private Object loadRatingEntity(RatingType ratingType, long l_ratingEntityId) throws IOException, ServletException {
		GOTDB2PersistenceManager pm	=	connect();
		
		try {
			
			switch(ratingType){
			case	character:
				return loadEntity(l_ratingEntityId, Entity.figure, pm);	
			case	episode:
				return loadEntity(l_ratingEntityId, Entity.episode, pm);
			case	house:
				return loadEntity(l_ratingEntityId, Entity.house, pm);
			case	season:
				return loadEntity(l_ratingEntityId, Entity.season, pm);
			default:
				throw new ServletException("Save rating undefined for rating type: " + ratingType );
			}
			
		} finally {
			disconnect(pm);
		}
	}

	private RatingType getRatingType(String ratingType) throws ServletException {
		try{
			return RatingType.valueOf(ratingType);	
		}catch (IllegalArgumentException e) {
			throw new ServletException("Unknown rating type: " + ratingType, e);
		}
	}

	private Transaction	beginTransaction() throws IOException{
		GOTDB2PersistenceManager pm = new GOTDB2PersistenceManager();
		
		try {
			 return pm.beginTransaction();
		} catch (PersistenceManagerException e){
			throw new IOException("Begin transaction failed", e);
		}
		
	}
	
	private void commit(Transaction transaction) throws IOException{
		try {
			transaction.commit(true);
		} catch (PersistenceManagerException e){
			throw new IOException("Commit transaction failed", e);
		}
	}

	private void close(Transaction transaction)	throws IOException {
		if(transaction == null)
			return;
		
		try {
			transaction.close();
		} catch (PersistenceManagerException e) {
			throw new IOException("Close transaction failed", e);
		}
	}
	
	private long insertEntity(Transaction transaction, Object entity, Entity type) throws IOException {
		try {
			return transaction.insertEntity(entity, type);
		} catch (PersistenceManagerException e){
			throw new IOException("Save entity: " + entity + " failed", e);
		}
		
	}

	private void updateEntity(Transaction transaction, Object entity, Entity type) throws IOException {
		try {
			transaction.updateEntity(entity, type);
		} catch (PersistenceManagerException e){
			throw new IOException("Update entity: " + entity + " failed", e);
		}
		
	}
	
}