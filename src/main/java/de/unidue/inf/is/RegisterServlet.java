package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;

public class RegisterServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super("register.ftl");
		this.loginNecessary = false;
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {		
	}
}