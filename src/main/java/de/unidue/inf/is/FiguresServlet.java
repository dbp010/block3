package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;

public class FiguresServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public FiguresServlet() {
		super("figures.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		List<Object>	figures		=	loadEntities(Entity.Figure, pm);
		
		req.setAttribute("figures", figures);
		
	}
}