package parser;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mysql.jdbc.PreparedStatement;

public class Connection {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/msdtest";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "msdteam99";
	
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	public static String insertTableSQL = null;

	public static void main(String[] argv) {

		try {

			insertRecordIntoDbUserTable();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	public static void insertRecordIntoDbUserTable() throws SQLException {
 
		java.sql.Connection dbConnection = null;
		Statement statement = null;

		try {
			File file = new File("/Users/meghnatulasi/Desktop/dblp.xml");  
			JAXBContext jaxbContext = JAXBContext.newInstance(Dblp.class);  

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			System.setProperty("javax.xml.accessExternalDTD", "all"); 
			System.setProperty("jdk.xml.maxGeneralEntitySizeLimit","0");
			System.setProperty("jdk.xml.entityExpansionLimit","0");
			Dblp data= (Dblp) jaxbUnmarshaller.unmarshal(file);

			dbConnection = getDBConnection();
			statement = ((java.sql.Connection) dbConnection).createStatement();


			////////////////////////////////////////////////////////////////
			if(data == null){
				throw new JAXBException("Error");
			}
			List<Phdthesis> list1 = data.getPhdthesis();
			System.out.println("Parsing Done");
			String insertSql = "INSERT INTO Thesis(author, title, year, school, ee, note, thesis_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);

			final int batchSize = 10000;
			int i=0;
			for(Phdthesis e:list1) {
				if(e.getAuthor()== null){ 
					continue;
				}
				if(!isUTF8MisInterpreted(e.gettitle())){
					continue;
				}
				

					if(!isUTF8MisInterpreted(e.getAuthor())){
						continue;
					}
					pstmt.setString(1, e.getAuthor()); //not sure if String or int or long
					pstmt.setString(2, e.gettitle());
					pstmt.setString(3, e.getyear());
					pstmt.setString(4, e.getschool());
					
					pstmt.setString(5, e.getee());
					pstmt.setString(6, e.getnote());
					pstmt.setString(7, e.getkey());

					pstmt.addBatch();
				}
				if(++i % batchSize == 0) {
					pstmt.executeBatch();
				}
			
			pstmt.executeBatch();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} 
		catch (JAXBException e) {  

			e.printStackTrace();  
		}
		finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				((java.sql.Connection) dbConnection).close();
			}

		}

	}

	/*// execute insert SQL statement for entering data in ARTICLE TABLE
			 List<Article> list1=que.getArticle();
			////////////////////////////////////////////////////////////////
			String insertSql = "INSERT INTO article(author, title, year, volume, journal, url, ee, article_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);

			final int batchSize = 10000;
			int i=0;
			for(Article e:list1) {
				/*i++;
				if(i==50000)
					break;
				if(e.getAuthor()== null){
	        		continue;
	        	}
				if(!isUTF8MisInterpreted(e.gettitle())){
					continue;
				}
			    pstmt.setString(1, e.getAuthor()); //not sure if String or int or long
			    pstmt.setString(2, e.gettitle());
			    pstmt.setString(3, e.getyear());	
			    pstmt.setString(4, e.getvolume());
			    pstmt.setString(5, e.getjournal());
			    pstmt.setString(6, e.geturl());
			    pstmt.setString(7, e.getee());
			    pstmt.setString(8, e.getkey());

			    pstmt.addBatch();

			    if(++i % batchSize == 0) {
			    	pstmt.executeBatch();
			    	}
			}
			pstmt.executeBatch();
			////////////////////////////////////////////////////////////////*/
	/*////////////////////////////////////////////////////////////////
List<Incollection> list1 = data.getIncollection();
String insertSql = "INSERT INTO incollection(author, title, year, book_title, url, ee, incoll_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);

final int batchSize = 100;
int i=0;
for(Incollection e:list1) {
if(e.getAuthor()== null){
continue;
}
if(!isUTF8MisInterpreted(e.gettitle())){
continue;
}
pstmt.setString(1, e.getAuthor()); //not sure if String or int or long
pstmt.setString(2, e.gettitle());
pstmt.setString(3, e.getyear());	
pstmt.setString(4, e.getbooktitle());
pstmt.setString(5, e.geturl());
pstmt.setString(6, e.getee());
pstmt.setString(7, e.getkey());

pstmt.addBatch();

if(++i % batchSize == 0) {
pstmt.executeBatch();
}
}
pstmt.executeBatch();*/


	private static boolean isUTF8MisInterpreted(String input) {
		String encoding ="Windows-1252";
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		CharsetEncoder encoder = Charset.forName(encoding).newEncoder();
		ByteBuffer tmp;
		try {
			tmp = encoder.encode(CharBuffer.wrap(input));
		}

		catch(CharacterCodingException e) {
			return false;
		}

		try {
			decoder.decode(tmp);
			return true;
		}
		catch(CharacterCodingException e){
			return false;
		}     
	}

	public static java.sql.Connection getDBConnection() {

		java.sql.Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
					DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	private static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());

	}

}