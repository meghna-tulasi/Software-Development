

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import QueryEngine.QueryEngineAuthor;
import QueryEngine.QueryEnginePaper;
import project.commons.Result;

public class QueryEnginePaperTest {

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
	public void retrievepaperrangetest() throws SQLException {
		List<String> confnames = new ArrayList<String>();
		confnames.add("OOPSLA");
		confnames.add("ECOOP");
		List<Result> r = QueryEnginePaper.retrievePaper("1","2", confnames, "1900","2012");
		int resultSize = r.size();
		System.out.println(resultSize); //3001
		assertEquals(4262,resultSize);
	}
	
	@Test
	public void retrievepaperatleasttest() throws SQLException {
		List<String> confnames = new ArrayList<String>();
		confnames.add("OOPSLA");
		confnames.add("ECOOP");
		List<Result> r = QueryEnginePaper.retrievePaper("1","", confnames, "1900","2012");
		int resultSize = r.size();
		System.out.println(resultSize); //3001
		assertEquals(5075,resultSize);
	}
	
	@Test
	public void retrievepersonrangetest() throws SQLException {
		List<String> confnames = new ArrayList<String>();
		confnames.add("OOPSLA");
		confnames.add("ECOOP");
		List<Result> r = QueryEnginePaper.retrievePerson("1","2", confnames, "1900","2012","2010","2012");
		int resultSize = r.size();
		System.out.println(resultSize); //3001
		assertEquals(4153,resultSize);
	}
	
	@Test
	public void retrievepersonatleasttest() throws SQLException {
		List<String> confnames = new ArrayList<String>();
		confnames.add("OOPSLA");
		confnames.add("ECOOP");
		List<Result> r = QueryEnginePaper.retrievePerson("1","", confnames, "1900","2012","2010","2012");
		int resultSize = r.size();
		System.out.println(resultSize); //3001
		assertEquals(4911,resultSize);
	}

}