package QueryEngine;

import java.util.List;

import project.commons.Result;

public interface QueryEngine {
	
	public static List<Result> retrieveAuthor(String s){
		return null;
	}
	
	public static List<Result> retrievePaper(String range1,String range2, List<String> confnames, String fromyear,String toyear){
		return null;
	}
	
	public static List<Result> retrievePerson(String range1, String range2, List<String> confnames, String fromyear,
			String toyear, String served_from, String served_till){
		return null;
	}
	
	public static List<Result> retrieveTitle(String s){
		return null;
	}

}
