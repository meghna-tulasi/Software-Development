

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

import parser.CommitteeParser;
import parser.Connection;
import project.commons.SingletonDBConnection;

public class ConnectionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore // we tested initially, parsing takes 10 minutes  	
	@Test

	public void connectionTest() throws SQLException
	{
		Connection.insertRecordIntoDbUserTable();
		java.sql.Connection dbConnection = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		Statement statement = null;
		//dbConnection = Connection.getDBConnection();
		statement = ((java.sql.Connection) dbConnection).createStatement();
		String selectSql = "SELECT * from Thesis";
		PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(selectSql);

		ResultSet rs = pstmt.executeQuery();
		int rowcount =0;
		if(rs.last())
		{
			rowcount=rs.getRow();
		}


		assertEquals(24226, rowcount);



	}
}
