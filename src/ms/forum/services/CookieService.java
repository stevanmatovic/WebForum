package ms.forum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CookieDao;
import ms.forum.dao.UserDao;
import ms.forum.model.User;

@Path("/cookie")
public class CookieService {

	@POST
	@Path("/user")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@CookieParam("stevan") String cookie) {

		CookieDao cookieDao = new CookieDao();
		int userId = cookieDao.getUserIdByCookie(cookie);
		UserDao userDao = new UserDao();
		User u = userDao.getById(userId);
		if(u != null)
			return Response.status(200).entity(u).build();
		else
			return Response.status(401).build();
	}
	
	
}
