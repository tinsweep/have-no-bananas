package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
/**
 * Created by Bryan on 6/18/2014.
 **/

public class DBConnectorTest {

	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	@Test
	public void testGetConnection() throws SQLException{
		try{
			//Arrange Test
			
			con = DBConnector.getConnection(con);
			
			//create a table to test
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS shoppingListTest("
					+ "ItemName VARCHAR(25), "
					+ "ItemQuantity INTEGER)");
			ps.execute();
			
			ps = con.prepareStatement("INSERT INTO shoppingListTest (ItemName, ItemQuantity) VALUES('milk', 1)");
			ps.execute();
			
			//retrieve results from table
			String query = "SELECT * FROM shoppingListTest";
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			}catch(SQLException e){
				throw e;
				
			}//end try/catch block
		
		//check results from table
		String Iq = "ItemQuantity";
		Integer check = 1;
		rs.next();
		Integer matcher = rs.getInt(Iq);
		
		assertEquals(check, matcher);
		
		//clean up
		//delete table
		ps = con.prepareStatement("DROP TABLE shoppingListTest");
		ps.execute();
		
		//close connection
		con.close();
	}//end method


}
