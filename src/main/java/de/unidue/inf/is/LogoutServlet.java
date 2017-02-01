package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.entity.User;

public class LogoutServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super("logout.ftl");
		this.loginNecessary = false;
	}

	@Override
	protected void appendAttributes(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		User		user		=	getUser(req, resp);

		req.getSession().removeAttribute("user");
		req.getSession().invalidate();
		
		boolean		loggedOut	=	true;
		
		req.setAttribute("logged_out",	loggedOut);
		req.setAttribute("user", 		user);
		
	}
	
	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {}
	
}