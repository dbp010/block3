package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.entity.Person;

public class PersonServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cid = String.valueOf(req.getParameter("cid"));
		
		System.out.println("Person id = " + cid);
		
		Person p;
		//p = GOTDB2PersistenceManager.getInstance().loadPerson(Long.valueOf(cid));
		p = new Person();
		p.setName("Klaus");
		
		req.setAttribute("person", p);
		req.getRequestDispatcher("person.ftl").forward(req, resp);
		
	}
	
}
