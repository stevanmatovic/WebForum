package ms.forum.services;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CookieDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Cookie;
import ms.forum.model.Credentials;
import ms.forum.model.SearchParams;
import ms.forum.model.User;

@Path("/users")
public class UserService {

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public String add(User user) {

		UserDao dao = new UserDao();
		dao.insert(user);
		return "OK";
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(Credentials cred) {

		Response response = null;
		UserDao userDAO = new UserDao();

		if (userDAO.getByUsername(cred.getUsername()) == null) {
			return Response.status(401).build();
		}
		
		String password = userDAO.getByUsername(cred.getUsername()).getPassword();
		int id = userDAO.getByUsername(cred.getUsername()).getId();
		
		
		if (password.equals(cred.getPassword())) {
			CookieDao dao = new CookieDao();
			String cookieId = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(cookieId,id);
			dao.insert(cookie);
			response = Response.status(200).entity(cookie).build();

		} else {
			response = Response.status(401).build();
		}
		return response;
	}

	
	@POST
	@Path("/search")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchAll(SearchParams json) {	
		
		Response response;
		
		UserDao userDao = new UserDao();
	    response = Response.status(200)
	    		.entity(userDao.searchByName(json.getKorisnik())).build();
	
		return response;
	}
	
}
