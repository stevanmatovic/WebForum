package ms.forum.services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CookieDao;
import ms.forum.dao.SubforumDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Post;
import ms.forum.model.SearchParams;
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
	
	@POST
	@Path("/getPosts")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Post> loadPost(String name){
		
		ArrayList<Post> list = new ArrayList<>();
		SubforumDao subforumDao = new SubforumDao();
		Subforum subforum = subforumDao.getByName(name);
		list = subforumDao.getListById(subforum.getId());
		return list;
	}
	
	@POST
	@Path("/menu")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendSubforums() {
		
		SubforumDao dao = new SubforumDao();
		return Response.status(200).entity(dao.getAll()).build();
	}
	
	@POST
	@Path("/search")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchAll(SearchParams json) {	
		
		Response response;
		
		SubforumDao podforumDao = new SubforumDao();
	    response = Response.
	    		status(200)
	    		.entity(podforumDao.search(json.getNaslov(), json.getOpis(), json.getModerator()))
	    		.build();
	
		return response;
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Subforum getSubforum(String name) {
		
		SubforumDao dao = new SubforumDao();
		Subforum s = dao.getByName(name);
		
		return s;
	}
	
}
