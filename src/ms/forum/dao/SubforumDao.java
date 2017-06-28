package ms.forum.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ms.forum.database.DataBaseConnection;
import ms.forum.model.Subforum;
import ms.forum.model.User;
import ms.forum.utils.DataBaseUtils;

public class SubforumDao {

	public void insert(Subforum subforum) {
		
		
		String sql = "INSERT INTO SUBFORUM (name,description,rules,main_moderator)"
				+ "VALUES (?, ?, ?)";
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

	

}
