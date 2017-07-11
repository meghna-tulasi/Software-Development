package QueryEngine;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import project.commons.Result;
import project.commons.SingletonDBConnection;

public class QueryEngineAuthor implements QueryEngine {

	private static final String SELECT_COLUMNS_INPROC = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.inproceeding";
	private static final String GROUPBY_AUTHOR = " group by author";
	private static final String GROUPBY_EDITOR = " group by editor";
	private static final String SELECT_COLUMNS_PROC = "select editor,title,year,url COLLATE utf8mb4_general_ci ,count(editor) from msd.proceeding";
	private static final String SELECT_COLUMNS_ARTICLE = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.article";
	
	
	@SuppressWarnings("unused")
	public static List<Result> retrieveAuthor(String s) throws SQLException
	{

		String author_condition = "";
		String editor_condition = "";
		java.sql.Connection dbConnection = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		
		Statement statement = null;

		
		statement = ((java.sql.Connection) dbConnection).createStatement();

		if(!s.isEmpty() || s != null){
			author_condition = author_condition.concat(" where author like ");
			author_condition = author_condition.concat("'%" + s + "%'");
			editor_condition = editor_condition.concat(" where editor like ");
			editor_condition = editor_condition.concat("'%" + s + "%'");
		}

		String selectAuthorQuery = SELECT_COLUMNS_INPROC + author_condition + GROUPBY_AUTHOR +
				" union " + SELECT_COLUMNS_PROC + editor_condition + GROUPBY_EDITOR +
				" union " + SELECT_COLUMNS_ARTICLE + author_condition + GROUPBY_AUTHOR;


		PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(selectAuthorQuery);
		ResultSet rs= pstmt.executeQuery();

		List<Result> results = new ArrayList<Result>();

		while(rs.next())
		{
			Result r = new Result(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			if(rs.getString(1) != null && rs.getString(2) != null && rs.getString(3)!= null && rs.getString(4)!= null && rs.getString(5)!= null)
			{
				results.add(r);
			}
		}

		return results;

	}


}
