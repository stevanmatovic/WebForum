package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Comment;
import ms.forum.model.Post;
import ms.forum.utils.DataBaseUtils;

public class CommentDao {

	public Comment insert(Comment comment) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO COMMENT (post_id,author_id,date,parent_id,text,changed,deleted)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {

			p = DataBaseConnection.getConnection().prepareStatement(sql);

			p.setInt(1, comment.getPostId());
			p.setInt(2, comment.getAuthorId());
			java.util.Date utilDate = new java.util.Date();
			p.setDate(3, new Date(utilDate.getTime()));
			p.setInt(4, comment.getParentId());
			p.setString(5, comment.getText());
			p.setBoolean(6, comment.isChanged());
			p.setBoolean(7, comment.isDeleted());
			p.execute();
			 try (ResultSet generatedKeys = p.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                comment.setId(generatedKeys.getInt(1));
		                return comment;
		            }
		            else {
		                throw new SQLException("Creating Komentar failed, no ID obtained.");
		            }
		        }			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(p);
		}
		return comment;
	}

	public Comment selectById(int komentarId) {
		String sql = "select * from Comment where id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, komentarId);
			rs = p.executeQuery();
			Comment komentar = new Comment();
			if (rs.next()) {
				this.fillEntityFromResultSet(komentar, rs);
			}
			return komentar;
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
	
	public List<Comment> selectByIdTema(int idTema) {
		String sql = "select * from Comment where post_id=? and parent_id=-1";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, idTema);
			rs = p.executeQuery();
			return this.processSelectAll(rs);
		} catch (SQLException e) {
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

	
	public List<Comment>  selectByParentIdPost(int parentId, int temaId) {
		String sql = "select * from Comment where parent_id=? and post_id=?";
		PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, parentId);
			p.setInt(2, temaId);
			rs = p.executeQuery();
			return this.processSelectAll(rs);
		} catch (SQLException e) {
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

	public void delete(int commentId) {

		String sql = "update Comment set deleted=1 where id=?";
		PreparedStatement p = null;
		try {
			
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setInt(1, commentId); 
			p.execute();
		} catch (SQLException e) {
    			e.printStackTrace();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		finally {
			DataBaseUtils.close(p);
		}
		

	}

	public void update(Comment comment) {

		String sql = "update Comment set post_id=?,author_id=?, date=?, parent_id=?,text=?,changed=?,deleted=? where id=?";
		PreparedStatement p = null;
		try {
			
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			
			p.setInt(1, comment.getPostId());
			p.setInt(2, comment.getAuthorId());
			java.util.Date utilDate = new java.util.Date();
			p.setDate(3, new Date(utilDate.getTime()));
			p.setInt(4, comment.getParentId());
			p.setString(5, comment.getText());
			p.setBoolean(6, true);
			p.setBoolean(7, comment.isDeleted());
			p.setInt(8, comment.getId()); 
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

	public int addNew(Comment comment) {
		// TODO Auto-generated method stub
				String sql = "INSERT INTO Comment (post_id,author_id,date, parent_id,text,changed,deleted)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement p = null;
				try {
					
					p = DataBaseConnection.getConnection().prepareStatement(sql);
					
					p.setInt(1, comment.getPostId());
					p.setInt(2, comment.getAuthorId());
					java.util.Date utilDate = new java.util.Date();
					p.setDate(3, new Date(utilDate.getTime()));
					p.setInt(4, comment.getParentId());
					p.setString(5, comment.getText());
					p.setBoolean(6, comment.isChanged());
					p.setBoolean(7, comment.isDeleted());
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
				
				return 0;
	}

	
	protected List<Comment> processSelectAll(ResultSet rs) throws SQLException {
		List<Comment> list = new ArrayList<Comment>();

		while (rs.next()) {
			Comment Comment = new Comment();
			fillEntityFromResultSet(Comment, rs);
			list.add(Comment);
		}

		return list;
	}

    
	
	
	private void fillEntityFromResultSet(Comment k, ResultSet rs) throws SQLException {
	    k.setId(rs.getInt("id"));
	    k.setPostId(rs.getInt("post_id"));
	    k.setAuthorId(rs.getInt("author_id"));
	    k.setDate(rs.getDate("date"));
	    k.setParentId(rs.getInt("parent_id"));
	    k.setText(rs.getString("text"));
	    k.setChanged(rs.getBoolean("changed"));
	    k.setDeleted(rs.getBoolean("deleted"));
	    
	    LikeCommentDao likeCommentDao = new LikeCommentDao();
	    k.setLikes(likeCommentDao.getLikeCount(k));
	    k.setDislikes(likeCommentDao.getDislikeCount(k));
	    
	}

	

}
