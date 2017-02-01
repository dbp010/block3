package de.unidue.inf.is;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Rating;
import de.unidue.inf.is.dbp010.db.entity.User;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public abstract class AGoTServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected static final String	DEFAULT_LOGIN_DISPATCH_LOCATION 	= 	"login";
	
	protected static final String	USER_ATTRIBUTE_NAME					=	"user";
	
	protected enum RatingType {
		character, episode, house, season
	}
	
	private final String templateName;
	
	private final String loginDispatchLocation;
	
	protected boolean loginNecessary = true;
	
	public AGoTServlet(String templateName) {
		this(templateName, DEFAULT_LOGIN_DISPATCH_LOCATION);
	}
	
	public AGoTServlet(String templateName, String loginTemplateName) {
		this.templateName 			= templateName;
		this.loginDispatchLocation 	= loginTemplateName;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(loginNecessary && getUser(req, resp) == null) {
			req.getRequestDispatcher(loginDispatchLocation).forward(req, resp);
			return;
		}
		
		appendAttributes(req, resp);

		dispatch(req, resp);
	}
	
	protected void appendAttributes(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		GOTDB2PersistenceManager pm = connect();
		
		appendAttributes(pm, req, resp);
		
		disconnect(pm);
	}

	protected void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(templateName).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	protected User getUser(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		return session == null ? null : (User) session.getAttribute("user");
	}

	protected void setUser(User user, HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(true);
		session.setAttribute(USER_ATTRIBUTE_NAME, user);
	}
	
	protected Object loadEntity(HttpServletRequest req, String idParamName, Entity type, GOTDB2PersistenceManager pm) throws IOException {
		
		String idParamValue = String.valueOf(req.getParameter(idParamName));
		
		Object entityObject = null;
		
		if(idParamValue != null && (idParamValue = idParamValue.trim()).length() > 0) {
			long id;

			try {
				
				id = Long.valueOf(idParamValue);
				return loadEntity(id, type, pm);
				
			}catch(NumberFormatException e){
				throw new IOException("Parsing id parameter {name:" + idParamName + " value: " + idParamValue + "} as number failed", e);
			}
		}
		
		return entityObject;
	}
	
	protected List<Object> loadEntities(Entity type, GOTDB2PersistenceManager pm) throws IOException {
		try {
			return pm.loadEntities(type);
		} catch (PersistenceManagerException e) {
			throw new IOException("Load entity objects of type: " + type + " failed", e);
		}
	}
	
	protected Object loadEntity(long id, Entity type, GOTDB2PersistenceManager pm) throws IOException {
		try {
			return pm.loadEntity(id, type);
		} catch (PersistenceManagerException e) {
			throw new IOException("Load entity object of type: " + type + "  with id: " + id + " failed", e);
		}
	}
	
	protected GOTDB2PersistenceManager connect() throws IOException {
		GOTDB2PersistenceManager pm = new GOTDB2PersistenceManager();
		try {
			pm.connect();
		} catch (PersistenceManagerException e) {
			throw new IOException(e);
		}
		return pm;
	}

	protected void disconnect(GOTDB2PersistenceManager pm) throws IOException {
		if(pm == null)	
			return;

		try {
			pm.disconnect();
		} catch (PersistenceManagerException e) {
			throw new IOException();
		}
	}
	
	protected void addRatingAttributes(RatingType type, long id, GOTDB2PersistenceManager pm,
	HttpServletRequest req, 	HttpServletResponse resp) throws IOException {
		
		Rating			userRating	=	null;
		List<Object>	ratings		=	null;
		
		switch(type){
			case character:
			try {
				ratings		=	pm.loadRatingsForCharacter(id);
			} catch (PersistenceManagerException e) {
				throw new IOException("Load ratings for character: " + id + " failed", e);
			}
				break;
			case episode:
			try {
				ratings		=	pm.loadRatingsForEpisode(id);
			} catch (PersistenceManagerException e) {
				throw new IOException("Load ratings for episode: " + id + " failed", e);
			}
				break;
			case house:
			try {
				ratings		=	pm.loadRatingsForHouse(id);
			} catch (PersistenceManagerException e) {
				throw new IOException("Load ratings for house: " + id + " failed", e);
			}
				break;
			case season:
			try {
				ratings		=	pm.loadRatingsForSeason(id);
			} catch (PersistenceManagerException e) {
				throw new IOException("Load ratings for season: " + id + " failed", e);
			}
				break;
			default:
		}
		
		float	avgRating	=	0;
		
		if(ratings != null && !ratings.isEmpty()){
			
			User	user		=	getUser(req, resp);
			int		count		=	ratings.size();
			
			for(Iterator<Object> i = ratings.iterator(); i.hasNext(); ){
				
				Rating rating 	= 	(Rating)i.next();
				
				avgRating 		+= 	rating.getRating();	
				
				if(rating.getUser().getUsid() == user.getUsid()){
					userRating = rating;
					i.remove();
				}
				
			}
			
			avgRating			/=	count;
		}
		
		
		req.setAttribute("user_rating", userRating);
		req.setAttribute("ratings",		ratings);
		req.setAttribute("avg_rating", 	avgRating);
		req.setAttribute("rating_type", type);
		
	}
	
	protected abstract void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException;
	
}
