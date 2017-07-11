import static org.junit.Assert.*;
 
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
 
import org.junit.Test;
 
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
 
import csrankings.CoAuthorsData;
import project.commons.SingletonDBConnection;
 
public class CoAuthorDataTest {
 
     
    HashMap<String, Set<String> > coAuthorsMap=new HashMap<String, Set<String> >();
    HashMap<String, String > coAuthorsMapWithCommaSeperatedCoAuthors= new HashMap<>();
     
     
    HashMap <String, String > result = new HashMap<>();
    String author="stephan";
    String coauthor="stephanie";
    Set<String> coauthorset= new HashSet<>(); 
     
     
    @Test
    public void getCommaSeperatedCoauthorstest()
    {
        coAuthorsMap.put(author, coauthorset);
        coauthorset.add(coauthor);
        coAuthorsMapWithCommaSeperatedCoAuthors.put(author, coauthor);
        result=CoAuthorsData.
                getCommaSeperatedCoauthorsFromCoAuthorMap(coAuthorsMap,coAuthorsMapWithCommaSeperatedCoAuthors);
        System.out.println(result);
         
    }
     
    @SuppressWarnings("static-access")
	@Test
    public void insertCoAuthorsToDBtest() throws SQLException
    {
        SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
        Connection dbConnection = singletonDBAccess.db.conn;
        CoAuthorsData.insertCoAuthorsToDB(coAuthorsMapWithCommaSeperatedCoAuthors);
        String insertSql = "INSERT INTO coauthors(author, coauthor) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(insertSql);
 
            pstmt.setString(1, author); 
            pstmt.setString(2, coAuthorsMapWithCommaSeperatedCoAuthors.get(author));
            pstmt.addBatch();
            int[] rs=pstmt.executeBatch();
            int rowcount = rs.length;
            assertEquals(1, rowcount);
 
        }
         
 
        catch (SQLException e) {
             
            e.printStackTrace();
        }
         
    }}