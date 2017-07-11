package csrankings;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.opencsv.CSVReader;

import project.commons.PropertyReader;
import project.commons.SingletonDBConnection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Muaaz
 * @version 1.0
 * @since 2017-04-15
 */
public class CoAuthorsData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Properties prop=PropertyReader.readProperties();

		String csvFile=prop.getProperty("coAuthorsCSVFilePath");


		HashMap<String, Set<String> > coAuthorsMap=new HashMap<String, Set<String> >();
		HashMap<String, String > coAuthorsMapWithCommaSeperatedCoAuthors= new HashMap<>();


		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));

			String[] line;
			String author="";

			while ((line = reader.readNext()) != null) {	
				author=line[0];


				if(coAuthorsMap.containsKey(author))
				{
					Set<String> coAuthorSet= new HashSet<>(); 
					coAuthorSet=coAuthorsMap.get(author);
					coAuthorSet.add(line[1]);
					coAuthorsMap.put(author, coAuthorSet);
				}
				else{

					Set<String> coAuthors= new HashSet<>();
					coAuthors.add(line[1]);

					coAuthorsMap.put(author, coAuthors);
				}


			} 
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		coAuthorsMapWithCommaSeperatedCoAuthors=getCommaSeperatedCoauthorsFromCoAuthorMap(coAuthorsMap,coAuthorsMapWithCommaSeperatedCoAuthors);

		// insert data to DB
		System.out.println("DB-Update Started for Co-authors");

		insertCoAuthorsToDB(coAuthorsMapWithCommaSeperatedCoAuthors);

		//printCoAuthorMap(coAuthorsMapWithCommaSeperatedCoAuthors);

		System.out.println("Database for co-Authors updated successfuly!!");

	}

	/**
	 * Gets the comma separated coauthor names from the coauthor map obtained from csv file.
	 * @param coAuthorsMap is a map from csv containing name of author and a set of coauthors
	 * @param coAuthorsMapWithCommaSeperatedCoAuthors
	 * @return
	 */
	public static HashMap<String, String> getCommaSeperatedCoauthorsFromCoAuthorMap(HashMap<String, Set<String>> coAuthorsMap,HashMap<String,String>  coAuthorsMapWithCommaSeperatedCoAuthors) {
		for(String authorName:coAuthorsMap.keySet()){

			HashSet<String> coAuthors=new HashSet<String>();

			coAuthors=(HashSet<String>) coAuthorsMap.get(authorName);
			StringBuilder coAuthorsCommaSeperated= new StringBuilder();

			for (String string : coAuthors) {
				coAuthorsCommaSeperated.append(",");

				coAuthorsCommaSeperated.append(string);

			}

			// remove the first char from the comma Seperated names
			coAuthorsCommaSeperated.deleteCharAt(0);
			coAuthorsMapWithCommaSeperatedCoAuthors.put(authorName, coAuthorsCommaSeperated.toString());
		}
		return coAuthorsMapWithCommaSeperatedCoAuthors;
	}

	/**
	 * Inserts the data from csv file taken from csranking.org to our database.
	 * @param coAuthorsMapWithCommaSeperatedCoAuthors the coauthors names that are obtained from the csv files
	 * @return nothing
	 */
	public static void insertCoAuthorsToDB(HashMap<String, String >coAuthorsMapWithCommaSeperatedCoAuthors){

		SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
		Connection dbConnection = singletonDBAccess.db.conn;

		String insertSql = "INSERT INTO coauthors(author, coauthor) VALUES (?, ?)";
		try {
			PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);


			for (String authorKey: coAuthorsMapWithCommaSeperatedCoAuthors.keySet()){
				pstmt.setString(1, authorKey); //not sure if String or int or long
				pstmt.setString(2, coAuthorsMapWithCommaSeperatedCoAuthors.get(authorKey));
				pstmt.addBatch();
			}

			pstmt.executeBatch();	

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}