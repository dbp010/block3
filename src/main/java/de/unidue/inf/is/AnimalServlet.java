package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Animal;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class AnimalServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cid = String.valueOf(req.getParameter("cid"));
		
		Animal a = null;
		
		if(cid != null && (cid = cid.trim()).length() > 0){			
			long id;
			try {
				 id = Long.valueOf(cid);
				 a = (Animal) GOTDB2PersistenceManager.getInstance().loadEntity(id, Entity.Animal);
			}catch(NumberFormatException e){
				new Exception("Parsing parameter cid as number failed", e).printStackTrace();
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load animal with id: " + cid + " failed", e);
			}
		}
		
		req.setAttribute("animal", a);
		req.getRequestDispatcher("animal.ftl").forward(req, resp);
		 
	}
	
}
