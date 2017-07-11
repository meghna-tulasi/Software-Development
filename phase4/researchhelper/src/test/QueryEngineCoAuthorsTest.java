import static org.junit.Assert.*;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import org.junit.Test;
 
import com.mysql.jdbc.Connection;
 
import project.commons.SingletonDBConnection;
import queryengine.QueryEngineCoAuthor;
 
public class QueryEngineCoAuthorsTest {
 
     
     
    @SuppressWarnings("static-access")
	@Test
    public void retrieveAuthorTest() throws SQLException 
    {
        List<String> results = new ArrayList<String>();
        results=QueryEngineCoAuthor.retrieveAuthor("stephan,stephanie,stef");
         
         
        String selectAuthorQuery = "";
         
         
        SingletonDBConnection singletonDBAccess=SingletonDBConnection.getDbCon();
        Connection dbConnection = singletonDBAccess.db.conn;
         
        selectAuthorQuery = "select coauthor from msd.coauthors where author = '" + "stephan,stephanie,stef" + "'"; 
         
            PreparedStatement pstmt = (PreparedStatement) dbConnection.prepareStatement(selectAuthorQuery);
 
             
            ResultSet rs=pstmt.executeQuery();
             
            while(rs.next())
            {
                String coauthors = rs.getString(1);
 
                String[] coauthor = coauthors.split(",");
 
                for(int i =0; i< coauthor.length;i++){
                    results.add(coauthor[i]);
                }
      
            }
             
            System.out.println(rs.getRow());
            int rowcount =0; 
            if(rs.last())
            {
                rowcount=rs.getRow();
            }
            assertEquals(0,rowcount);
    }
}