package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Cookie;
import ms.forum.model.User;
import ms.forum.utils.DataBaseUtils;

public class CookieDao {

	public void insert(Cookie cookie){
		
		String sql = "INSERT INTO COOKIE (id,user_id,expiration_date)"
				+ "VALUES (?, ?, ?)";
		PreparedStatement p = null;
		try{
			p = DataBaseConnection.getConnection().prepareStatement(sql);
			p.setString(1, cookie.getId());
			p.setInt(2, cookie.getUserId());
			p.setDate(3, cookie.getExpirationDate());
			
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DataBaseUtils.close(p);
		}	
	}	
	
	public int getUserIdByCookie(String id){
		
		String sql = "select * from COOKIE where id=?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = DataBaseConnection.getConnection().prepareStatement(sql);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("user_id");
			}else{
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			DataBaseUtils.close(resultSet, statement);
		}
		
	}
}
