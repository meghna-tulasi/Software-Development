

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import QueryEngine.QueryEnginePaper;
import QueryEngine.QueryEngineTitle;
import project.commons.Result;

public class QueryEngineKeywordTest {

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
	public void retrievetitletest() throws SQLException {
		List<Result> result = new ArrayList<Result>();
		String s="generic,virtual";
		
	 result = QueryEngineTitle.retrieveTitle(s);
		
		int resultSize = result.size();
		System.out.println(resultSize); //3001
		assertEquals(2,resultSize);
	}
	
}