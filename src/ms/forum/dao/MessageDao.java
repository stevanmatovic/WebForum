package ms.forum.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Comment;
import ms.forum.model.Message;
import ms.forum.model.User;
import ms.forum.utils.DataBaseUtils;

public class MessageDao {

	
	public int add(Message poruka) {
		// TODO Auto-generated method stub
				String sql = "INSERT INTO poruka (prima,salje,subject,tekst)"
						+ "VALUES (?, ?, ?, ?)";
				PreparedStatement p = null;
				try {
					
					p = DataBaseConnection.getConnection().prepareStatement(sql);
					
					p.setInt(1, poruka.getPrima());
					p.setInt(2, poruka.getSalje());
					p.setString(3, poruka.getSubject());
					p.setString(4, poruka.getTekst());
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

	public Message getById(int idPoruke) {
		String sql = "select * from poruka where id=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, idPoruke);
			resultSet = statement.executeQuery();
			Message m = new Message();
			if (resultSet.next()) {
				mapEntityFromResultSet(m, resultSet);
			}
			return m;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(resultSet, statement);

		}
		return null;
	}

	public ArrayList<Message> getByUserId(int prima) {

		String sql = "select * from poruka where prima=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, prima);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return processSelectAll(resultSet);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(resultSet, statement);

		}
		return null;
		
	}
	
	protected ArrayList<Message> processSelectAll(ResultSet rs) throws SQLException {
		ArrayList<Message> list = new ArrayList<Message>();

		while (rs.next()) {
			Message m = new Message();
			mapEntityFromResultSet(m, rs);
			list.add(m);
		}

		return list;
	}
	
	private void mapEntityFromResultSet(Message m, ResultSet rs) throws SQLException {
		
		m.setId(rs.getInt("id"));
		m.setPrima(rs.getInt("prima"));
		m.setSalje(rs.getInt("salje"));
		UserDao userDao = new UserDao();
		String name = userDao.getById(m.getSalje()).getUsername();
		m.setSenderName(name);
		m.setSubject(rs.getString("tekst"));

	}
}
