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

public class QueryEngineTitle implements QueryEngine{

	private static final String SELECT_COLUMNS_INPROC = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.inproceeding";
	private static final String GROUPBY_AUTHOR = " group by author";
	private static final String GROUPBY_EDITOR = " group by editor";
	private static final String SELECT_COLUMNS_PROC = "select editor,title,year,url COLLATE utf8mb4_general_ci ,count(editor) from msd.proceeding";
	private static final String SELECT_COLUMNS_ARTICLE = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.article";


	@SuppressWarnings("unused")
	public static List<Result> retrieveTitle(String s) throws SQLException {

		String title_condition = "";
		java.sql.Connection dbConnection = null;
		Statement statement = null;


		if(!s.isEmpty() || s != null){
			String[] s1;
			s1 =s.split(",");

			int length=s1.length;
			for(int i=0;i<length;i++)
			{
				if(i < 1) {
					title_condition = title_condition.concat(" where title like ");
					title_condition = title_condition.concat("'%" + s1[i] + "%'");
				}
				else if(i>=1){
					title_condition = title_condition.concat(" and title like ");
					title_condition = title_condition.concat("'%" + s1[i] + "%'");
				}
			}
		}

		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		//dbConnection = getDBConnection();
		statement = ((java.sql.Connection) dbConnection).createStatement();
		String selectTitleQuery = SELECT_COLUMNS_INPROC +  title_condition + GROUPBY_AUTHOR + 
				" union " + SELECT_COLUMNS_PROC + title_condition + GROUPBY_EDITOR +
				" union " + SELECT_COLUMNS_ARTICLE + title_condition + GROUPBY_AUTHOR;



		PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(selectTitleQuery);

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

		return  results;

	}

	

}



