package ms.forum.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import ms.forum.dao.CommentDao;
import ms.forum.dao.CookieDao;
import ms.forum.dao.LikeCommentDao;
import ms.forum.dao.PostDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Comment;
import ms.forum.model.LikeComment;
import ms.forum.model.User;

@Path("/comment")
public class CommentService {

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(@CookieParam("stevan") String cookie,Comment comment) {

		CookieDao cookieDao = new CookieDao();
		int userId = cookieDao.getUserIdByCookie(cookie);

		UserDao userDao = new UserDao();
		User u = userDao.getById(userId);
		if(u == null)
			return Response.status(401).build();
		
		comment.setAuthorId(userId);

		if(comment.getPostId() == -1){
			CommentDao commentDao = new CommentDao();
			int postId = commentDao.selectById(comment.getParentId()).getPostId();
			comment.setPostId(postId);
		}
		
		CommentDao commentDao = new CommentDao();

		comment = commentDao.insert(comment);
		
		return Response.status(200).entity(comment).build();
		
	}
	
	@POST
	@Path("/get")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComment(String input) {
		int id = Integer.parseInt(input);
		CommentDao commentDao = new CommentDao();
		Comment comment = commentDao.selectById(id);
		
		return Response.status(200).entity(comment).build();
		
	}
	
	@POST
	@Path("/getAuthor")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentAuthor(String input) {
		int id = Integer.parseInt(input);
		CommentDao commentDao = new CommentDao();
		Comment comment = commentDao.selectById(id);
		UserDao userDao = new UserDao();
		User u = userDao.getById(comment.getAuthorId());
		
		return Response.status(200).entity(u).build();
		
	}
	
	
	@POST
	@Path("/subcomments")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public List<Comment> loadComments(String input){
		input = input.replace("#", "");
		int id = Integer.parseInt(input);
		
		CommentDao commentDao = new CommentDao();
		Comment c = commentDao.selectById(id);
		int postId = c.getPostId();
		
		return commentDao.selectByParentIdPost(id, postId);
	}
	
	@POST
	@Path("/like")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response likeKomentar(LikeComment likeKomentar,@CookieParam("stevan") String value) {	
		Response response;
		CommentDao komentarDao = new CommentDao();
		CookieDao cookieDao = new CookieDao();
		LikeCommentDao likeKomentarDao = new LikeCommentDao();
		int userId = cookieDao.getUserIdByCookie(value);
		if(userId == -1){
			response = Response.status(405).build();
			return response;
		}
		likeKomentar.setIdUser(userId);
		likeKomentarDao.addNew(likeKomentar);
	    response = Response
	    		.status(200)
	    		.entity(komentarDao.selectById(likeKomentar.getIdKomentar()))
	    		.build();

		return response;
	}
	
}
