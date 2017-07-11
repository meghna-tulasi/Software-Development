package queryengine;

import java.sql.SQLException;
import java.util.List;

import project.commons.Result;

/**
 * @author Bansal Shah
 * @version 1.0
 */
public class QueryEngineAuthor extends QueryEngineAuthorFactory {

	static QueryEngineAuthorFactory factory = new QueryEngineAuthorFactory();

	/**
	 * Function to retrieve author on Author Name basis
	 * @param s the name of author that needs to be matched in the records.
	 * @return the list of result object matching the given name
	 * @throws SQLException if the query being run throws some Exception
	 */
	@SuppressWarnings("static-access")
	public static List<Result> retrieveAuthor(String s) throws SQLException
	{
		List<Result> results = factory.retrieveAuthor(s);

		return results;
	}
}
