package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class PersonServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Person 			person			= (Person) loadEntity(req, "cid", Entity.Person);

		List<Object> 	relationships 	= null;
		List<Object> 	animals 		= null;
		List<Object>	members			= null;
		
		if(person != null){
			
			try {
				relationships	= GOTDB2PersistenceManager.getInstance().loadRelationshipsBySourcepid(person.getCid());
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load relationships by person cid: " + person.getCid() + " failed", e).printStackTrace();
			}
			
			try {
				animals			= GOTDB2PersistenceManager.getInstance().loadAnimalsByOwner(person.getCid());
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load animals by person cid: " + person.getCid() + " failed", e).printStackTrace();
			}
			 
			try {
				members			= GOTDB2PersistenceManager.getInstance().loadMembersByPid(person.getCid());
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load members by person cid: " + person.getCid() + " failed", e).printStackTrace();
			}
		}
		
		req.setAttribute("person", 			person);
		req.setAttribute("members", 		members);
		req.setAttribute("relationships", 	relationships);
		req.setAttribute("animals", 		animals);
		
		req.getRequestDispatcher("person.ftl").forward(req, resp);
		 
	}
}