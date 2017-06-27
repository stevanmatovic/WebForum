package ms.forum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	private static Connection connection;
	
	public static void setConnection(Connection c){
		connection = c;
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
	{
    	if (connection == null){
    		Class.forName("org.sqlite.JDBC").newInstance();
    		connection = DriverManager.getConnection("jdbc:sqlite:C:\\FAKULTET\\6.semestar\\Web\\REST\\JQuery_REST_reseno\\WebForum\\database\\forum.db");	
    	}
	    
		return connection; 
	}	
}
