package ms.forum.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	
}
