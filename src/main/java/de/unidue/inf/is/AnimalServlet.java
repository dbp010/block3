package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Animal;

public class AnimalServlet extends AGoTServlet {
	
	public AnimalServlet() {
		super("animal.ftl");
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		Animal 			animal 		= 	(Animal) 	loadEntity(req, "cid", Entity.Animal, pm);
		
		if(animal != null) {
			
			addRatingAttributes(RatingType.Character, animal.getCid(), pm, req, resp);
		
		}
		
		req.setAttribute("animal", 		animal);
	}

	
}