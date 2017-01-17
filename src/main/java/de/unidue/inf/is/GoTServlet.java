package de.unidue.inf.is;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.entity.Person;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.utils.DBUtil;

/**
 * Das könnte die Eintrittsseite sein.
 */
public final class GoTServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String databaseToCheck = "mygot";
		boolean databaseExists = DBUtil.checkDatabaseExistsExternal(databaseToCheck);

		request.setAttribute("db2name", databaseToCheck);

		if (databaseExists) {
//			request.setAttribute("db2exists", "vorhanden! Supi!");
			System.out.println("Datenbank " + databaseToCheck + " vorhanden! Supi!");
		}
		else {
			request.setAttribute("db2exists", "nicht vorhanden :-(");
			System.out.println("Datenbank " + databaseToCheck + " nicht vorhanden :-(");
		}

		List<Person> pl  = new ArrayList<>();
		Person p = new Person();
		p.setCid(45345345);
		p.setName("Klaus");
		pl.add(p);
		
		/* fetch person via persistence manager */
		
		
		request.setAttribute("persons", pl);
		
		request.getRequestDispatcher("got_start.ftl").forward(request, response);
	}

	
}
