package ms.forum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CookieDao;
import ms.forum.dao.MessageDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Message;

@Path("/message")
public class MessageService {

	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response add(Message poruka,@CookieParam("stevan") String value) {	
		
		Response response = null;
		
		UserDao userDAO = new UserDao();
		
		CookieDao cookieDao = new CookieDao();
		int idSender = cookieDao.getUserIdByCookie(value);
		
		if(idSender == 0){
			response = Response.status(405).build();
			return response;
		}
		poruka.setSalje(idSender);
		MessageDao porukaDao = new MessageDao();
		
		porukaDao.add(poruka);
		
		return Response.status(200).build();
	}
	
	
	
	
	@POST
	@Path("/get")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public Response get(@CookieParam("stevan") String value, int messageId) {	
		
		Response response = null;
		UserDao userDAO = new UserDao();
		
		CookieDao cookieDao = new CookieDao();
		int id = cookieDao.getUserIdByCookie(value);
		
		MessageDao messageDao = new MessageDao();
		Message m = messageDao.getById(messageId);
		
		if(id ==  0 || id == -1 || m.getPrima() != id){
			response = Response.status(401).build();
			return response;
		}
		
		
		return Response.status(200).entity(m).build();
	}
	
	
	@POST
	@Path("/getMessages")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getMessages(@CookieParam("stevan") String value) {	
		
		Response response = null;
		UserDao userDAO = new UserDao();
		
		CookieDao cookieDao = new CookieDao();
		int id = cookieDao.getUserIdByCookie(value);
		
		if(id ==  0 || id == -1){
			response = Response.status(401).build();
			return response;
		}
		MessageDao messageDao = new MessageDao();
		
		return Response.status(200).entity(messageDao.getByUserId(id)).build();
	}
	
	
	
}
