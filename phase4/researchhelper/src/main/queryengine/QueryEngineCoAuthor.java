package queryengine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import project.commons.SingletonDBConnection;

/**
 * @author Bansal Shah
 * @version 1.0
 */
public class QueryEngineCoAuthor {

	/**
	 * Function to retrieve co-author on Author Name basis
	 * @param s the name of author of whose co-authors need to be find
	 * @return the list of string which is the list of co-author names
	 * @throws SQLException if the query being run throws some Exception
	 */
	public static List<String> retrieveAuthor(String s) throws SQLException
	{

		java.sql.Connection dbConnection = null;
		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		dbConnection = singletonDBAccess.db.conn;
		String selectAuthorQuery = "";
		PreparedStatement pstmt;
		ResultSet rs = null;

		Statement statement = null;

		statement = ((java.sql.Connection) dbConnection).createStatement();

		if(!s.isEmpty() && s != null){
			selectAuthorQuery = "select coauthor from msd.coauthors where author = '" + s + "'"; 

			pstmt = (PreparedStatement) dbConnection.prepareStatement(selectAuthorQuery);
			System.out.println(pstmt);
			rs= pstmt.executeQuery();
		}


		List<String> results = new ArrayList<String>();

		while(rs.next())
		{
			String coauthors = rs.getString(1);

			String[] coauthor = coauthors.split(",");

			for(int i =0; i< coauthor.length;i++){
				results.add(coauthor[i]);
			}

		}
		System.out.println(results);
		return results;

	}


}