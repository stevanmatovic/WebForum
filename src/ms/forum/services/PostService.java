package ms.forum.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ms.forum.dao.CookieDao;
import ms.forum.dao.PostDao;
import ms.forum.dao.SubforumDao;
import ms.forum.model.Post;
import ms.forum.model.Subforum;


@Path("/post")
public class PostService {
	
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(InputStream uploadedInputStream) {
		
		String fileLocation = "C:\\FAKULTET\\6.semestar\\Web\\REST\\JQuery_REST_reseno\\WebForum\\WebContent\\images\\"; // fileDetail.getFileName();
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
	
	
}
