

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import QueryEngine.QueryEngineAuthor;
import project.commons.*;
public class QueryEngineAuthorTest {




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

	

	@Test
	public void retrieveauthortest() throws SQLException {
		List<Result> r = QueryEngineAuthor.retrieveAuthor("Stefan Appel");
		Result r1 = r.get(0);
		System.out.println(r1.toString());
		Result output = new Result("Stefan Appel", "Implementing Federated Object Systems.", "2013", "db/conf/ecoop/ecoop2013.html#FreudenreichEFAB13", "1");
		System.out.println(output.toString());
		assertEquals(output,r1);
	}

	@Ignore // to do
	@Test 
	public void dbconnectiontest() 
	{
		final String DB_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_CONNECTION = "jdbc:mysql://localhost:3306/msd";
		final String DB_USER = "root";
		final String DB_PASSWORD = "Chinnu";

		java.sql.Connection dbConnection = null;
		try {
			dbConnection= DriverManager.getConnection(DB_CONNECTION,DB_DRIVER,DB_USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(null,dbConnection);
	}
	@Ignore // to do
	@Test
	public void dbconnectiontest1() 
	{
		final String DB_DRIVER = "com.mysql.oracle.Driver";
		final String DB_CONNECTION = "jdbc:mysql://localhost:3306/msd";
		final String DB_USER = "root";
		final String DB_PASSWORD = "msdteam99";

		java.sql.Connection dbConnection = null;
		fail("throws exception");
		
	}

}


