package UI;

import java.util.List;

import Model.Author;
import QueryEngine.QueryExecuter;
import QueryEngine.Result;
import QueryEngine.SearchInput;

public class App implements QueryExecuter {

	@Override
	public List<Result> executeQuery(SearchInput searhinputs) {
		// implementation based on search inputs
		return null;
	}

	@Override
	public List<Result> filterResults(List<?> filterCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> sortResults(List<?> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Result> similarProfile(Author author) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
