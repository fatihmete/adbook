package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

	private static Connection connection;
	
	public DB() {
		
		try {
    		Class.forName("org.sqlite.JDBC");
        	String temp = System.getProperty("java.io.tmpdir");

    		connection = DriverManager.getConnection("jdbc:sqlite:" + temp + "app.sqlite3");
			
		} catch (Exception e) {
			// TODO: handle exception
			connection = null;
		}
		
	}

	
	public static Connection getConnection() throws SQLException {
	
		if(connection==null) {
			new DB();
		}
		if(connection.isClosed()) {
			new DB();
		}
		return connection;
	}
	

}
