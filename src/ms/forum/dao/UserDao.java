package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.User;
import ms.forum.utils.DataBaseUtils;

public class UserDao {

	public User getById(int id) {

		String sql = "select * from USER where id=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			User u = new User();
			if (resultSet.next()) {
				mapEntityFromResultSet(u, resultSet);
			}
			return u;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtils.close(resultSet, statement);

		}
		return null;

	}

	public void insert(User user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO user (username,name,surname,password,role,phone_number,email,date)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement p = null;
		try {

			p = DataBaseConnection.getConnection().prepareStatement(sql);

			p.setString(1, user.getUsername());
			p.setString(2, user.getName());
			p.setString(3, user.getSurname());
			p.setString(4, user.getPassword());
			// p.setString(5, user.getRole());
			p.setString(6, user.getPhoneNumber());
			java.util.Date utilDate = new java.util.Date();
			p.setString(7, user.getEmail());
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

	public User getByUsername(String username) {

		String sql = "select * from USER where username=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			User u = new User();
			if (resultSet.next()) {
				mapEntityFromResultSet(u, resultSet);
			}else{
				u = null;
			}
			
			return u;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DataBaseUtils.close(resultSet, statement);
		}

	}

	private void mapEntityFromResultSet(User u, ResultSet resultSet) throws SQLException {

		u.setId(resultSet.getInt("id"));
		u.setDate(resultSet.getString("date"));
		u.setEmail(resultSet.getString("email"));
		u.setName(resultSet.getString("name"));
		u.setPassword(resultSet.getString("password"));
		u.setPhoneNumber(resultSet.getString("phone_number"));
		u.setRole(resultSet.getString("role"));
		u.setSurname(resultSet.getString("surname"));
		u.setUsername(resultSet.getString("username"));

	}

}
