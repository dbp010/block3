package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;

public class SeasonsServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public SeasonsServlet() {
		super("seasons.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		List<Object>	seasons		=	loadEntities(Entity.Season, pm);
		
		req.setAttribute("seasons", seasons);
		
	}
}