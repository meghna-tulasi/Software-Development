package QueryEngine;
import java.util.List;

import Model.Author;

import Model.Paper;
public interface QueryExecuter {
	
	// take inputs as searchInput Class and generate apprprate query based on those params
	// returns search Result
	// we can mock data here for searchIP params and also for ip data
	
	
	
	public  List<Result>  executeQuery(SearchInput searhinputs);
	public  List<Result>  filterResults(List<?> filterCriteria);
	public  List<?>  sortResults(List<?> criteria);
	public  List<Result>  similarProfile(Author author);
	
	
	
	

}
