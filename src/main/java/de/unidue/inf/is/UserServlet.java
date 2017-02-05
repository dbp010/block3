package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;

public class UserServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super("user.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		
		req.setAttribute("page_title", 	"User page");
	}

}
