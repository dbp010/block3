package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;

public abstract class AGoTBasicServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	private final String templateName;
	
	public AGoTBasicServlet(String templateName) {
		this.templateName = templateName;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		GOTDB2PersistenceManager pm = connect();
		
		appendAttributes(pm, req, resp);
		
		disconnect(pm);
		
		req.getRequestDispatcher(templateName).forward(req, resp);
		
	}

	protected abstract void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException;
	
}
