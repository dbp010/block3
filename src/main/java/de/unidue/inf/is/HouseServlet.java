package de.unidue.inf.is;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager.Entity;
import de.unidue.inf.is.dbp010.db.entity.House;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public class HouseServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		House			house	=	(House) loadEntity(req, "hid", Entity.House);

		List<Object>	members			= null;
		List<Object> 	belongings 		= null;
		
		if(house != null){
			
			try {
				members		= GOTDB2PersistenceManager.getInstance().loadMembersByHid(house.getHid());
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load members by house hid: " + house.getHid() + " failed", e).printStackTrace();
			}
			
			try {
				belongings	= GOTDB2PersistenceManager.getInstance().loadBelongingsByHid(house.getHid());
			} catch (PersistenceManagerException e) {
				new PersistenceManagerException("Load belongings by house hid: " + house.getHid() + " failed", e).printStackTrace();
			}
		}
		
		req.setAttribute("house", 			house);
		req.setAttribute("belongings", 		belongings);
		req.setAttribute("members", 		members);
		
		req.getRequestDispatcher("house.ftl").forward(req, resp);
		 
	}
	
}
