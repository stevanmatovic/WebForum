package ms.forum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ms.forum.dao.CookieDao;
import ms.forum.dao.SubforumDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Subforum;
import ms.forum.model.User;

@Path("/subforum")
public class SubforumService {

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@CookieParam("stevan") String cookie,Subforum subforum) {
		
		CookieDao cookieDao = new CookieDao();
		int moderatorId = cookieDao.getUserIdByCookie(cookie);
		subforum.setModeratorId(moderatorId);
		SubforumDao dao = new SubforumDao();
		dao.insert(subforum);
		return "OK";
	}
	
}
