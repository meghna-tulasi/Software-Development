package queryengine;

import java.sql.SQLException;
import java.util.List;

import project.commons.Result;


/**
 * @author Meghna Tulasi
 * @version 1.0
 */
public class QueryEnginePaper implements QueryEngine{

	static QueryEnginePaperFactory factory = new QueryEnginePaperFactory();

	/**
	 * Function to retrieve author on number of papers published in particular conferences/journals
	 * along with a year range
	 * @param range1 minimum number of papers published
	 * @param range2 maximum number of papers published
	 * @param confnames name(s) of conferences author has published papers in
	 * @param fromyear starting year of publications done by author
	 * @param toyear ending year of publications done by author
	 * @return the list of result object matching the given criteria
	 * @throws SQLException if the query that is ran throws Sql error
	 */
	@SuppressWarnings("static-access")
	public static List<Result> retrievePaper(String range1,String range2, List<String> confnames, String fromyear,String toyear) throws SQLException
	{

		List<Result> results = factory.retrievePaper(range1,range2,confnames,fromyear,toyear);

		return results;
	}
	
	/**
	 * Function to retrieve author on number of papers published in particular conferences/journals
	 * along with a year range for publication and also for served in a committee.
	 * @param range1 minimum number of papers published
	 * @param range2 maximum number of papers published
	 * @param confnames name(s) of conferences author has published papers in
	 * @param fromyear starting year of publications done by author
	 * @param toyear ending year of publications done by author
	 * @param served_from starting year of being member of a committee
	 * @param served_till ending year of being member of a committee
	 * @return the list of result object matching the given criteria
	 * @throws SQLException if the query that is ran throws Sql Error
	 */
	@SuppressWarnings("static-access")
	public static List<Result> retrievePerson(String range1, String range2, List<String> confnames, String fromyear,
			String toyear, String served_from, String served_till) throws SQLException {
		List<Result> results = factory.retrievePerson(range1,range2,confnames,fromyear,toyear, served_from,served_till);

		return results;
	}
}