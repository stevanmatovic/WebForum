package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Post;
import ms.forum.utils.DataBaseUtils;

public class PostDao {

	public void insert(Post post) {
        // TODO Auto-generated method stub
        String sql = "INSERT INTO POST (subforum_id,title,type,author_id,content_text,content_picture,content_link,date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement p = null;
        try {
           
            p = DataBaseConnection.getConnection().prepareStatement(sql);
           
            p.setInt(1, post.getSubforumId());
            p.setString(2, post.getTitle());
            p.setString(3, post.getType());
            p.setInt(4, post.getAutorId());
            p.setString(5, post.getTekst());
            p.setString(6, post.getPicture()); 
            p.setString(7, post.getLink());
            java.util.Date utilDate = new java.util.Date();
            p.setDate(8, new Date(utilDate.getTime()));
            p.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            DataBaseUtils.close(p);
        }
       
    }


	
	
}
