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


public class QueryEnginePaper implements QueryEngine{

	private static final String SELECT_COLUMNS_INPROC = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.inproceeding";
	private static final String GROUPBY_AUTHOR = " group by author";
	private static final String GROUPBY_EDITOR = " group by editor";
	private static final String SELECT_COLUMNS_PROC = "select editor,title,year,url COLLATE utf8mb4_general_ci ,count(editor) from msd.proceeding";
	private static final String SELECT_COLUMNS_ARTICLE = "select author,title,year,url COLLATE utf8mb4_general_ci ,count(author) from msd.article";
	private static final String SELECT_AUTHORS_COMM = "(select distinct member from msd.committee";


	@SuppressWarnings("unused")
	public static List<Result> retrievePaper(String range1,String range2, List<String> confnames, String fromyear,String toyear) throws SQLException
	{
		PreparedStatement pstmt=null;

		java.sql.Connection dbConnection = null;
		Statement statement = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		//dbConnection = getDBConnection();
		statement = ((java.sql.Connection) dbConnection).createStatement();
		String selectAuthorQuery;
		String fromYear_condition = "", toYear_condition = "";

		String inproc_condition = getConfCondition(confnames,"inproc_key");
		String proc_condition = getConfCondition(confnames,"proc_key");
		String article_condition = getjournalCondition(confnames,"article_key");

		if(!fromyear.isEmpty() && fromyear != null){
			if(!inproc_condition.isEmpty()){
				fromYear_condition = " and year >= " + fromyear;
			}
			else {
				fromYear_condition = " where year >= " + fromyear;
			}
		}

		if(!toyear.isEmpty() && toyear != null){
			if(!inproc_condition.isEmpty() || !fromYear_condition.isEmpty()){
				toYear_condition = " and year <= " + toyear;
			}
			else if(fromYear_condition.isEmpty()) {
				toYear_condition = " where year <= " + toyear;
			}
		}


		String having_author_condition = "";
		String having_editor_condition = "";




		if(range2.isEmpty())
		{
			if(!range1.isEmpty())
			{
				having_author_condition = " having count(author) >= " + range1;
				having_editor_condition = " having count(editor) >= " + range1;
			}

			selectAuthorQuery = SELECT_COLUMNS_INPROC + inproc_condition + fromYear_condition + toYear_condition + GROUPBY_AUTHOR + having_author_condition +
					" union " + SELECT_COLUMNS_PROC +  proc_condition + fromYear_condition + toYear_condition + GROUPBY_EDITOR + having_editor_condition +
					" union " + SELECT_COLUMNS_ARTICLE + article_condition + fromYear_condition + toYear_condition + GROUPBY_AUTHOR + having_author_condition;

			pstmt = (PreparedStatement) dbConnection.prepareStatement(selectAuthorQuery);
		}

		else
		{

			if(!range1.isEmpty())
			{
				having_author_condition = " having count(author) >= " + range1;
				having_editor_condition = " having count(editor) >= " + range1;
				having_author_condition = having_author_condition.concat(" and count(author) <= " + range2);
				having_editor_condition = having_editor_condition.concat(" and count(editor) <= " + range2);
			}
			selectAuthorQuery = SELECT_COLUMNS_INPROC + inproc_condition + fromYear_condition + toYear_condition + GROUPBY_AUTHOR + having_author_condition +
					" union " + SELECT_COLUMNS_PROC +  proc_condition + fromYear_condition + toYear_condition + GROUPBY_EDITOR + having_editor_condition +
					" union " + SELECT_COLUMNS_ARTICLE + article_condition + fromYear_condition + toYear_condition + GROUPBY_AUTHOR + having_author_condition;


			pstmt = (PreparedStatement) dbConnection.prepareStatement(selectAuthorQuery);
		}

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

		System.out.println(results.size());
		return results;

	}

	@SuppressWarnings("unused")
	public static List<Result> retrievePerson(String range1, String range2, List<String> confnames, String fromyear,
			String toyear, String served_from, String served_till) throws SQLException {

		PreparedStatement pstmt=null;


		java.sql.Connection dbConnection = null;
		Statement statement = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;

		//dbConnection = getDBConnection();
		statement = ((java.sql.Connection) dbConnection).createStatement();
		String checkCommitteeQuery;

		String fromYear_condition = "", toYear_condition = "",served_from_condition = "", served_till_condition = "", existsIn_condition = "";

		String inproc_condition = getConfCondition(confnames,"inproc_key");

		if(!fromyear.isEmpty() && fromyear != null){
			if(!inproc_condition.isEmpty()){
				fromYear_condition = " and year >= " + fromyear;
			}
			else {
				fromYear_condition = " where year >= " + fromyear;
			}
		}

		if(!toyear.isEmpty() && toyear != null){
			if(!inproc_condition.isEmpty() || !fromYear_condition.isEmpty()){
				toYear_condition = " and year <= " + toyear;
			}
			else if(fromYear_condition.isEmpty()) {
				toYear_condition = " where year <= " + toyear;
			}
		}

		if(!served_from.isEmpty() && served_from != null){
			served_from_condition = " where year >= " + served_from;
		}

		if(!served_till.isEmpty() && served_till != null){
			if(!served_from_condition.isEmpty()){
				served_till_condition = " and year <= " + served_till;
			}
			else if(served_from_condition.isEmpty()) {
				served_till_condition = " where year <= " + served_till;
			}
		}

		if(fromYear_condition.isEmpty() && toYear_condition.isEmpty() && inproc_condition.isEmpty()){
			existsIn_condition = " where author not in";
		}
		else{
			existsIn_condition = " and author not in";
		}

		String having_author_condition = "";


		if(range2.isEmpty())
		{
			if(!range1.isEmpty())
			{
				having_author_condition = " having count(author) >= " + range1;
			}

			checkCommitteeQuery = SELECT_COLUMNS_INPROC + inproc_condition + fromYear_condition + toYear_condition +
					existsIn_condition + SELECT_AUTHORS_COMM + served_from_condition + served_till_condition + ")" +
					GROUPBY_AUTHOR + having_author_condition;

			pstmt = (PreparedStatement) dbConnection.prepareStatement(checkCommitteeQuery);
		}

		else
		{

			if(!range1.isEmpty())
			{
				having_author_condition = " having count(author) >= " + range1;
				having_author_condition = having_author_condition.concat(" and count(author) <= " + range2);
			}
			else{
				having_author_condition = " having count(author) <= " + range2;
			}
			checkCommitteeQuery = SELECT_COLUMNS_INPROC + inproc_condition + fromYear_condition + toYear_condition +
					existsIn_condition + SELECT_AUTHORS_COMM + served_from_condition + served_till_condition + 
					")" + GROUPBY_AUTHOR + having_author_condition;

			pstmt = (PreparedStatement) dbConnection.prepareStatement(checkCommitteeQuery);
		}

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

		System.out.println(results.size());
		return results;

	}

	private static String getjournalCondition(List<String> confnames, String table_key) {
		String conf_condition = "";
		if(!confnames.isEmpty() || confnames != null){

			for(int i=0;i<confnames.size();i++)
			{
				if(i < 1) {
					conf_condition = conf_condition.concat(" where " + table_key + " like ");
					conf_condition = conf_condition.concat("'journals/" + confnames.get(i) + "/%'");
				}
				else if(i>=1){
					conf_condition = conf_condition.concat(" or " + table_key + " like ");
					conf_condition = conf_condition.concat("'journals/" + confnames.get(i) + "/%'");
				}
			}
		}
		return conf_condition;	
	}

	private static String getConfCondition(List<String> confnames, String table_key) {
		String conf_condition = "";
		if(!confnames.isEmpty() || confnames != null){

			for(int i=0;i<confnames.size();i++)
			{
				if(i < 1) {
					conf_condition = conf_condition.concat(" where " + table_key + " like ");
					conf_condition = conf_condition.concat("'conf/" + confnames.get(i) + "/%'");
				}
				else if(i>=1){
					conf_condition = conf_condition.concat(" or " + table_key + " like ");
					conf_condition = conf_condition.concat("'conf/" + confnames.get(i) + "/%'");
				}
			}
		}
		return conf_condition;
	}

	

}







