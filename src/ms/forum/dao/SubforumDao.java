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
import ms.forum.model.User;
import ms.forum.utils.DataBaseUtils;

public class SubforumDao {

	public void insert(Subforum subforum) {
		
		
		String sql = "INSERT INTO SUBFORUM (name,description,rules,main_moderator)"
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement p = null;
		try {

			p = DataBaseConnection.getConnection().prepareStatement(sql);

			p.setString(1, subforum.getName());
			p.setString(2, subforum.getDescription());
			p.setString(3, subforum.getRules());
			p.setInt(4, subforum.getModeratorId());
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

	public ArrayList<Subforum> getAll() {
		String sql = "select * from SUBFORUM";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection()
						.prepareStatement(sql);
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
	protected ArrayList<Subforum> processSelectAll(ResultSet rs) throws SQLException {
		ArrayList<Subforum> list = new ArrayList<Subforum>();

		while (rs.next()) {
			Subforum podforum = new Subforum();
			fillUserFromResultSet(podforum, rs);
			list.add(podforum);
		}
		

		return list;
	}

	private void fillUserFromResultSet(Subforum p, ResultSet rs) throws SQLException {
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setDescription(rs.getString("description"));
		p.setModeratorId(rs.getInt("main_moderator"));
		p.setRules(rs.getString("rules"));
	}

	public Subforum getByName(String name) {
		// TODO Auto-generated method stub
		name = name.replaceAll("#", "");
		int id = Integer.parseInt(name);
		String sql = "select * from SUBFORUM where id=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			Subforum s = new Subforum();
			if (resultSet.next()) {
				mapEntityFromResultSet(s, resultSet);
			}
			return s;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(resultSet, statement);

		}
		return null;
	}
	
	
	private void mapEntityFromResultSet(Subforum s, ResultSet resultSet) throws SQLException {

		s.setId(resultSet.getInt("id"));
		s.setName(resultSet.getString("name"));
		s.setDescription(resultSet.getString("description"));
		s.setRules(resultSet.getString("rules"));
		s.setModeratorId(resultSet.getInt("main_moderator"));

	}
	

	protected ArrayList<Post> getPostList(ResultSet rs) throws SQLException {
		ArrayList<Post> list = new ArrayList();
 
        while (rs.next()) {
            Post post = new Post();
            fillPostFromResultSet(post, rs);
            list.add(post);
        }
 
        return list;
    }
   
    public ArrayList<Post> getListById(int podforumId) {
        String sql = "select * from POST where subforum_id = ?";
        PreparedStatement p = null;
        ResultSet rs = null;
        try {
            p = DataBaseConnection.getConnection()
                        .prepareStatement(sql);
            p.setInt(1, podforumId);
            rs = p.executeQuery();
 
            return this.getPostList(rs);
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
    }
	
    public List<Subforum> search(String Naslov, String Opis, String moderatorUser) {
		String sql;			
		sql = "select f.* from subforum f left join user u on f.main_moderator=u.id where f.name like ? and f.description like ?  and u.username like ?";
    	PreparedStatement p = null;
		ResultSet rs = null;
		try {
			p = DataBaseConnection.getConnection()
						.prepareStatement(sql);
			p.setString(1, "%"+Naslov+"%");
			p.setString(2, "%"+Opis+"%");
			p.setString(3, "%"+moderatorUser+"%");

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

    
}
