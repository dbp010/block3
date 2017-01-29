package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class PersonServlet extends AGoTBasicServlet {

	private static final long serialVersionUID = 1L;

	public PersonServlet() {
		super("person.ftl");
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Person 			person			= (Person) loadEntity(req, "cid", Entity.Person, pm);

		List<Object> 	relationships 	= null;
		List<Object> 	animals 		= null;
		List<Object>	members			= null;
		
		if(person != null){
			
			try {
				relationships	= pm.loadRelationshipsBySourcepid(person.getCid());
			} catch (PersistenceManagerException e) {
				throw new IOException("Load relationships by sourcepid: " + person.getCid() + " failed", e);
			}
			
			try {
				animals			= pm.loadAnimalsByOwner(person.getCid());
			} catch (PersistenceManagerException e) {
				throw new IOException("Load animals by owner: " + person.getCid() + " failed", e);
			}
			 
			try {
				members			= pm.loadMembersByPid(person.getCid());
			} catch (PersistenceManagerException e) {
				throw new IOException("Load members by person: " + person.getCid() + " failed", e);
			}
		}
		
		req.setAttribute("person", 			person);
		req.setAttribute("members", 		members);
		req.setAttribute("relationships", 	relationships);
		req.setAttribute("animals", 		animals);
		 
	}
}