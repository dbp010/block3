package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.User;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class LoginServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super("login.ftl");
		this.loginNecessary = false;
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		User	user	=	getUser(req, resp);

		// START: ONLY FOR DEVELOPMENT !!! {
		if(user == null){
		
			try {
				user	=	(User)	pm.loadEntity(1, Entity.User);
				setUser(user, req, resp);
			} catch (PersistenceManagerException e) {
				throw new IOException("Load user failed", e);
			}
			
		}
		// } END
				
		req.setAttribute("user", user);
	}
	
}