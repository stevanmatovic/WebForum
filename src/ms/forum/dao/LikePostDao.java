package ms.forum.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.LikePost;
import ms.forum.model.Post;
import ms.forum.utils.DataBaseUtils;

public class LikePostDao {

	
	public LikePost selectById(int id) {
		String sql = "select * from like_post where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			LikePost lt = new LikePost();
			if (rs.next()) {
				this.fillEntityFromResultSet(lt, rs);
			}
			return lt;
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}
		return null;
	}

	
	public LikePost selectByPostAndUser(int idPost, int idUser) {
		String sql = "select * from like_post where post_id=? and user_id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		LikePost lt = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idPost);
			p.setInt(2, idUser);
			rs = p.executeQuery();
			if (rs.next()) {
				lt = new LikePost();
				this.fillEntityFromResultSet(lt, rs);
			}
			return lt;
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}

		return null;
	}

// selectBy se koristi za selectByPost i selectByUser	
	private List<LikePost> selectBy(int id, String sql){
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection()
						.prepareStatement(sql);
			p.setInt(1,id); 
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
	
	public List<LikePost> selectByPost(int idPost) {
	  	String sql = "select * from like_post where post_id=?";
	  	return selectBy(idPost,sql);

	}

	
	public List<LikePost> selectByUser(int idUser) {
	  	String sql = "select * from like_post where user_id=?";
	  	return selectBy(idUser,sql);

	}

	private Boolean doAddNew(LikePost lt){		
		String sql = "INSERT INTO LIKE_POST (post_id,user_id, LIKED) VALUES (?,?,?)";
		PreparedStatement p = null;
		try {			
			p = DataBaseConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lt.getIdTema());
			p.setInt(2, lt.getIdUser());
			p.setBoolean(3,lt.getLike());
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(p);
		}
		return null;		
	}
	
	public Boolean addNew(LikePost lt) {
	  // ako like ne postoji, dodajem ga, ako postoji radi se update
	  if (selectByPostAndUser(lt.getIdTema(), lt.getIdUser()) == null)		  
         return doAddNew(lt);
	  else
		 return update(lt); 	  
	}

	
	public Boolean update(LikePost lt) {
		String sql = "UPDATE LIKE_POST SET post_id=?, user_id=?, LIKED=? WHERE post_id=? and user_id=? ";
		PreparedStatement p = null;
		try {			
			p = DataBaseConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lt.getIdTema());
			p.setInt(2, lt.getIdUser());
			p.setBoolean(3,lt.getLike());
			p.setInt(4, lt.getIdTema());
			p.setInt(5, lt.getIdUser());
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(p);
		}
		return null;
	}

	
	public Boolean delete(int id) {
		String sql = "DELETE FROM LIKE_POST WHERE ID=?";
		PreparedStatement p = null;
		try {			
			p = DataBaseConnection.getConnection().prepareStatement(sql);			
			p.setInt(1,id);
			return p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(p);
		}
		return null;
	}
	
	private int getPostLikeDislikeCount(int idPost, int likeDislike){
		String sql = "select count(*) broj from like_post where post_id=? and liked=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idPost);
			p.setInt(2, likeDislike);
			rs = p.executeQuery();
			if (rs.next()) {
				return rs.getInt("broj");
			}
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}
		return 0;
		
	}
	
	public int getPostLikeCount(int idPost) {
        return getPostLikeDislikeCount(idPost,1); 		
	}

	
	public int getPostDislikeCount(int idPost) {
        return getPostLikeDislikeCount(idPost,0); 	
	}

	
	public int getPostLikeCount(Post tema) {
		return getPostLikeCount(tema.getId());
	}

	
	public int getPostDislikeCount(Post tema) {
		return getPostDislikeCount(tema.getId());
	}

	
	protected List<LikePost> processSelectAll(ResultSet rs) throws SQLException {
		List<LikePost> list = new ArrayList<LikePost>();

		while (rs.next()) {
			LikePost lt = new LikePost();
			fillEntityFromResultSet(lt, rs);
			list.add(lt);
		}

		return list;
	}

    
	private void fillEntityFromResultSet(LikePost lt, ResultSet rs) throws SQLException {
		lt.setId(rs.getInt("id"));
		lt.setIdTema(rs.getInt("post_id"));
		lt.setIdUser(rs.getInt("user_id"));
		lt.setLike(rs.getBoolean("liked"));		
	}
	
}
