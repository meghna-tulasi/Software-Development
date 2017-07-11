package queryengine;

import java.sql.SQLException;
import java.util.List;

import project.commons.Result;

/**
 * @author Bansal Shah
 * @version 1.0
 */
public class QueryEngineTitle implements QueryEngine{

	static QueryEngineTitleFactory factory = new QueryEngineTitleFactory();
	
	/**
	 * Function to retrieve author on title's keyword basis
	 * @param s the keywords present in a publication title
	 * @return the list of result object matching the given keywords
	 * @throws SQLException
	 */
	@SuppressWarnings({"static-access" })
	public static List<Result> retrieveTitle(String s) throws SQLException {

		List<Result> results = factory.retrieveTitle(s);

		return  results;

	}
}