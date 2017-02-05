package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.dbp010.db.GOTDB2PersistenceManager;
import de.unidue.inf.is.dbp010.db.entity.User;
import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;
import de.unidue.inf.is.utils.CryptUtil;

public class LoginServlet extends AGoTServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super("login.ftl");
		this.loginNecessary = false;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User	user	=	getUser(req, resp);
		String	error	=	null;
		
		String	f		=	req.getParameter("f");
		
		if(f	!=	null	&&	user == null){
			
			String	p		=	req.getParameter("p");
			String	l		=	req.getParameter("l");
			
			
			boolean valid	=	(p 	!= 	null 
							&&	(p 	= 	p.trim()).length() > 0
							&&	l	!=	null
							&&	(l	=	l.trim()).length() > 0);
			
			if(valid){
				
				GOTDB2PersistenceManager pm = connect();
				
				try {
					user	=	pm.loadUserByLogin(l);
				} catch (PersistenceManagerException e) {
					throw new IOException("Load user by login: " + l + " failed", e);
				}
				finally {
					disconnect(pm);
				}
				
			}
			
			if(user != null){
				String sha1		=	CryptUtil.createSHA1Hash(p);
				String check	=	sha1.substring(0, 31);
				
				if(!user.getPassword().equals(check)){
					user = null;
				}
			}
			
			if( user == null) {
				error = "credentials_incorrect";
			}
			else
				user.setPassword(null);
				setUser(user, req, resp);
			
		}
		
		req.setAttribute("user", 	user);
		req.setAttribute("error",	error);
		
		dispatch(req, resp);
	}

	@Override
	protected void appendAttributes(GOTDB2PersistenceManager pm, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {}
	
}