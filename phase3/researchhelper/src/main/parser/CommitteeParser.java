package parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import com.mysql.jdbc.PreparedStatement;

public class CommitteeParser {
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/msdtest?useServerPrepStmts=false&rewriteBatchedStatements=true";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "msdteam99";
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static void main(String[] args) {  
		
		committeeParse();
		
	}
	
	public static void committeeParse()
	{
		
		
		
		try {
			File folder = new File("/Users/meghnatulasi/Downloads/committees");
			File[] listOfFiles = folder.listFiles();

			List<Committee> committees = new ArrayList<Committee>();
			java.sql.Connection dbConnection = null;
			Statement statement = null;
			dbConnection = getDBConnection();
			statement = ((java.sql.Connection) dbConnection).createStatement();

			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];

				List<Member> memberList = new ArrayList<Member>();
				String position = null;
				String line = null;
				//System.out.println(i + " " + file.getName());
				File f = new File(file.getName());
				Pattern p = Pattern.compile("[a-z]+|\\d+");
				Matcher m = p.matcher(file.getName());
				ArrayList<String> allMatches = new ArrayList<>();
				while (m.find()) {
				    allMatches.add(m.group());
				}
				FileReader sc = new FileReader("/Users/meghnatulasi/Downloads/committees/" + f);
				BufferedReader bufferedReader = 
		                new BufferedReader(sc);
				while((line = bufferedReader.readLine()) != null){
					Member member = new Member();
					if(line.contains(":")){
						String[] details = line.split(":");
						member.setPosition(details[0]);
						member.setName(details[1]);
					}
					else{
						member.setPosition("M");
						member.setName(line);
					}
					memberList.add(member);
				}
			
				Committee c = new Committee(allMatches.get(0), allMatches.get(1), memberList);
				committees.add(c);

			}
			
			String insertSql = "INSERT INTO committee(commName, year, position, member) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);
			
			for(Committee e:committees) {
				for(Member member:e.getMembers()){
					if(member == null){
						continue;
					}

					pstmt.setString(1, e.getCommName()); //not sure if String or int or long
					pstmt.setString(2, e.getYear());
					pstmt.setString(3, member.getPosition());
					pstmt.setString(4, member.getName());

					pstmt.addBatch();
				} 
			}
			
			pstmt.executeBatch();
		}
		catch (Exception e) {         
			e.printStackTrace();
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

}