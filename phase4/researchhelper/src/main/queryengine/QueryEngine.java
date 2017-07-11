package queryengine;

import java.util.List;

import project.commons.Result;

/**
 * @author Bansal Shah
 * @version 1.0
 */
public interface QueryEngine {
	
	/**
	 * Function to retrieve author on Author Name basis
	 * @param s the name of author that needs to be matched in the records.
	 * @return the list of result object matching the given name
	 */
	public static List<Result> retrieveAuthor(String s){
		return null;
	}
	
	/**
	 * Function to retrieve author on number of papers published in particular conferences/journals
	 * along with a year range
	 * @param range1 minimum number of papers published
	 * @param range2 maximum number of papers published
	 * @param confnames name(s) of conferences author has published papers in
	 * @param fromyear starting year of publications done by author
	 * @param toyear ending year of publications done by author
	 * @return the list of result object matching the given criteria
	 */
	public static List<Result> retrievePaper(String range1,String range2, List<String> confnames, String fromyear,String toyear){
		return null;
	}
	
	/**
	 * Function to retrieve author on number of papers published in particular conferences/journals
	 * along with a year range along with year range for served in a committee
	 * @param range1 minimum number of papers published
	 * @param range2 maximum number of papers published
	 * @param confnames name(s) of conferences author has published papers in
	 * @param fromyear starting year of publications done by author
	 * @param toyear ending year of publications done by author
	 * @param served_from starting year of being member of a committee
	 * @param served_till ending year of being member of a committee
	 * @return the list of result object matching the given criteria
	 */
	public static List<Result> retrievePerson(String range1, String range2, List<String> confnames, String fromyear,
			String toyear, String served_from, String served_till){
		return null;
	}
	
	/**
	 * Function to retrieve author on title's keyword basis
	 * @param s the keywords present in a publication title
	 * @return the list of result object matching the given keywords
	 */
	public static List<Result> retrieveTitle(String s){
		return null;
	}

}
