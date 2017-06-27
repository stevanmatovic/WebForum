package ms.forum.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtils {

	
	public static void close(ResultSet rs, Statement st)
	{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception e) { }
		}
		
		if (st != null) {
			try {
				st.clearWarnings();
				st.close();
			}
			catch (Exception e) { }
		}
	}

	public static void close(ResultSet rs)
	{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception e) { }
		}
	}

	public static void close(Statement st)
	{
		if (st != null) {
			try {
				st.clearWarnings();
				st.close();
			}
			catch (SQLException e) { }
		}
	}
	
}
