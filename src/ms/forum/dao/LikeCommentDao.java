package ms.forum.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Comment;
import ms.forum.model.LikeComment;
import ms.forum.utils.DataBaseUtils;

public class LikeCommentDao {

	public LikeComment selectById(int id) {
		String sql = "select * from like_comment where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, id);
			rs = p.executeQuery();
			LikeComment l = new LikeComment();
					if (rs.next()) {
				this.fillEntityFromResultSet(l, rs);
			}
			return l;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}
		return null;
	}

	
	public List<LikeComment> selectByComment(int idComment) {
	  	String sql = "select * from like_comment where comment_id=?";
	  	PreparedStatement p = null;
			ResultSet rs = null;
			try {
				p = DataBaseConnection.getConnection()
							.prepareStatement(sql);
				p.setInt(1,idComment); 
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

	
	public LikeComment selectByCommentAndUser(int idComment, int idUser) {
		String sql = "select * from like_comment where comment_id=? and user_id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		LikeComment lt = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idComment);
			p.setInt(2, idUser);
			rs = p.executeQuery();
			if (rs.next()) {
				lt = new LikeComment();
				this.fillEntityFromResultSet(lt, rs);
			}
			return lt;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}

		return null;
	}
	private Boolean doAddNew(LikeComment lK){		
		String sql = "INSERT INTO LIKE_COMMENT (COMMENT_ID,USER_ID, LIKED) VALUES (?,?,?)";
		PreparedStatement p = null;
		try {			
			p = DataBaseConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, lK.getIdKomentar());
			p.setInt(2, lK.getIdUser());
			p.setBoolean(3,lK.getLike());
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
	
	public Boolean addNew(LikeComment l) {
		  // ako like ne postoji, dodajem ga, ako postoji radi se update
		  if (selectByCommentAndUser(l.getIdKomentar(), l.getIdUser()) == null)		  
	         return doAddNew(l);
		  else
			 return update(l); 
	}

	public Boolean update(LikeComment l) {
		String sql = "UPDATE LIKE_Comment SET COMMENT_ID=?, USER_ID=?, LIKED=? WHERE COMMENT_ID=? and USER_ID=? ";
		PreparedStatement p = null;
		try {			
			p = DataBaseConnection.getConnection().prepareStatement(sql);			
			p.setInt(1, l.getIdKomentar());
			p.setInt(2, l.getIdUser());
			p.setBoolean(3,l.getLike());
			p.setInt(4, l.getIdKomentar());
			p.setInt(5, l.getIdUser());
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
		String sql = "DELETE FROM LIKE_COMMENT WHERE ID=?";
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
	private int getLikeDislikeCount(int idComment, int likeDislike){
		String sql = "select count(*) broj from LIKE_COMMENT where COMMENT_ID=? and LIKED=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idComment);
			p.setInt(2, likeDislike);
			rs = p.executeQuery();
			if (rs.next()) {
				return rs.getInt("broj");
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(rs, p);
		
		}
		return 0;
	}
	
	
	public int getLikeCount(int idComment) {
		return getLikeDislikeCount(idComment, 1);
	}

	
	public int getLikeCount(Comment komentar) {
		
		return getLikeCount(komentar.getId());
	}

	
	public int getDislikeCount(int idComment) {
		
		return getLikeDislikeCount(idComment,0);
	}

	
	public int getDislikeCount(Comment komentar) {
		
		return getDislikeCount(komentar.getId());
	}
	
	
	protected List<LikeComment> processSelectAll(ResultSet rs) throws SQLException {
		List<LikeComment> list = new ArrayList<LikeComment>();

		while (rs.next()) {
			LikeComment l = new LikeComment();
			fillEntityFromResultSet(l, rs);
			list.add(l);
		}

		return list;
	}

    
	private void fillEntityFromResultSet(LikeComment l, ResultSet rs) throws SQLException {
		l.setId(rs.getInt("id"));
		l.setIdKomentar(rs.getInt("COMMENT_ID"));
		l.setIdUser(rs.getInt("USER_ID"));
		l.setLike(rs.getBoolean("LIKED"));		
	}
	
}
