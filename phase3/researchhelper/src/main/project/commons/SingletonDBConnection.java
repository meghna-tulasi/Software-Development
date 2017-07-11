package project.commons;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Properties;
/**
 * @desc A singleton database access class for MySQL
 * @author Muaaaz
 */
public final class SingletonDBConnection {
	public Connection conn;
	private Statement statement;
	public static SingletonDBConnection db;
	private SingletonDBConnection() {
		String url= "jdbc:mysql://conferencemanager.ckl4qy7segyh.us-west-2.rds.amazonaws.com:3306/";
		String dbName = "msd";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "msdteam";
		String password = "msdteam99";

		// get the property values
		
		Properties prop=PropertyReader.readProperties();
		
		prop.getProperty("url");
		prop.getProperty("dbName");
		prop.getProperty("driver");
		prop.getProperty("userName");
		prop.getProperty("password");


		try {
			Class.forName(driver).newInstance();
			this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
		}
		catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	/**
	 *
	 * @return MysqlConnect Database connection object
	 */
	public static synchronized SingletonDBConnection getDbCon() {
		if ( db == null ) {
			db = new SingletonDBConnection();
		}
		return db;

	}
}