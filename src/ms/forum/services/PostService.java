package ms.forum.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CommentDao;
import ms.forum.dao.CookieDao;
import ms.forum.dao.LikePostDao;
import ms.forum.dao.PostDao;
import ms.forum.dao.SubforumDao;
import ms.forum.dao.UserDao;
import ms.forum.model.Comment;
import ms.forum.model.LikePost;
import ms.forum.model.Post;
import ms.forum.model.SearchParams;
import ms.forum.model.Subforum;
import ms.forum.model.User;


@Path("/post")
public class PostService {
	
	@Context
	ServletContext context;
	
	@POST
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public Post getPost(String input){
		input = input.replace("#", "");
		int id = Integer.parseInt(input);
		
		PostDao postDao = new PostDao();
		
		Post p = postDao.getById(id); 
		return p;
	}
	
	@POST
	@Path("/loadComments")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public List<Comment> loadComments(String input){
		input = input.replace("#", "");
		int id = Integer.parseInt(input);
		
		CommentDao commentDao = new CommentDao();
		
		return commentDao.selectByIdTema(id);
	}
	
	
	
	@POST
	@Path("getAuthor")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.TEXT_PLAIN})
	public String getUsername(String input){
		input = input.replace("#", "");
		int id = Integer.parseInt(input);
		
		PostDao postDao = new PostDao();
		
		Post p = postDao.getById(id);
		UserDao userDao = new UserDao();
		User author = userDao.getById(p.getAutorId());
		return author.getUsername();
	}
	
	@POST
	@Path("/like")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response like(LikePost like,@CookieParam("stevan") String value){
	
		CookieDao cookieDao = new CookieDao();
	    LikePostDao likeDao = new LikePostDao();
		int userId = cookieDao.getUserIdByCookie(value);
		like.setIdUser(userId);
		likeDao.addNew(like);
		if(userId == 0){
			return Response.status(401).build();
		}
		PostDao postDao = new PostDao();
		Post p =  postDao.getById(like.getIdTema());
		return Response.status(200).entity(p).build();
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(InputStream uploadedInputStream) {
		
		System.out.println(context.getRealPath(""));
		
		
		String fileLocation = context.getRealPath("") + "\\images\\";
        String imageId = UUID.randomUUID().toString()  + ".jpg";
        fileLocation += imageId;
		try {
			FileOutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			//out = new FileOutputStream(new File(fileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String output = imageId;
		return Response.status(200).entity(output).build();
	}
	
	@POST
    @Path("/add")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response add(Post post,@CookieParam("stevan") String value) {
       
        Response response;
       
        PostDao postDao = new PostDao();
        SubforumDao subforumDao = new SubforumDao();
        CookieDao cookieDao = new CookieDao();
       
        if(post.getTekst() == null){
        	post.setTekst("");
        }
        
        int userId = cookieDao.getUserIdByCookie(value);
        post.setAutorId(userId);
        
        Subforum parent = subforumDao.getByName(post.getHelp());
        post.setSubforumId(parent.getId());
        
        if(userId != 0){
            postDao.insert(post);
            response = Response.status(200).build();
        }else{
            response = Response.status(401).build();
        }
        return response;
    }
	
	@POST
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response search(SearchParams json) {	
		Response response;		
		PostDao temaDao = new PostDao();
	    response = Response.
	    		status(200)
	    		.entity(temaDao.search(json.getNaslov(),json.getSadrzaj(),json.getAutor(),json.getPodforum()))
	    		.build();
	
		return response;
	}	
	
}
