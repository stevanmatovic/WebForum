package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Post;
import ms.forum.model.Subforum;
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
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(p);
		}

	}

	public List<Post> search(String naslov, String sadrzaj, String autor, String podforum) {
		String sql;
		sql= "select t.* from post t left join user a on t.author_id=a.id left join subforum pf on t.subforum_id=pf.id where title like ? and content_text like ? and a.username like ? and pf.name like ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, "%"+naslov+"%");
			p.setString(2, "%"+sadrzaj+"%");
			p.setString(3, "%"+autor+"%");
			p.setString(4, "%"+podforum+"%");
			rs = p.executeQuery();

			return this.processSelectAll(rs);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		}
    	return null;
	}
	
	protected List processSelectAll(ResultSet rs) throws SQLException {
		List list = new ArrayList();

		while (rs.next()) {
			Post tema = new Post();
			fillPostFromResultSet(tema, rs);
			list.add(tema);
		}

		return list;
	}
	
	public Post getById(int id) {

		String sql = "select * from POST where id=?" ;
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			Post post = new Post();
			fillPostFromResultSet(post, rs);
			return post;
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		}
    	return null;
		
	}
	

    
    private void fillPostFromResultSet(Post post, ResultSet rs) throws SQLException {
    	post.setId(rs.getInt("id"));
        post.setAutorId(rs.getInt("author_id"));
        post.setDate(rs.getDate("date"));
        post.setLink(rs.getString("content_link"));
        post.setPicture(rs.getString("content_picture"));
        post.setSubforumId(rs.getInt("subforum_id"));
        post.setTekst(rs.getString("content_text"));
        post.setTitle(rs.getString("title"));
        post.setType(rs.getString("type"));
        
        LikePostDao likePostDao = new LikePostDao();
		post.setLikes(likePostDao.getPostLikeCount(post.getId()));
		post.setDislikes(likePostDao.getPostDislikeCount(post.getId()));
	}
    
}
