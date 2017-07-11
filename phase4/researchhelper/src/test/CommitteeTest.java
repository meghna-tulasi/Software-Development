

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

import junit.framework.Assert;
import parser.Committee;
import parser.CommitteeParser;
import project.commons.Result;
import project.commons.SingletonDBConnection;

public class CommitteeTest {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/msdtest?useServerPrepStmts=false&rewriteBatchedStatements=true";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "msdteam99";
	
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

	
	
	
	@Ignore // tested initially 
	@Test
	
	public void committeeTest() throws SQLException
	{
		CommitteeParser.committeeParse();
		java.sql.Connection dbConnection = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		Statement statement = null;
		//dbConnection = CommitteeParser.getDBConnection();
		statement = ((java.sql.Connection) dbConnection).createStatement();
		String selectSql = "SELECT * from committee";
		PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(selectSql);
		
		ResultSet rs = pstmt.executeQuery();
		int rowcount =0;
		if(rs.last())
		{
			rowcount=rs.getRow();
		}
		
		
			assertEquals(6321, rowcount);
			
		
		
	}
}
