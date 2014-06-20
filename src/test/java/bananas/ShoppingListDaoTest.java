package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by Bryan on 6/7/2014.
 **/
public class ShoppingListDaoTest {
	DBConnector c;
	Connection conn;
	//establish connection to database
	@Before
	protected void setup() throws SQLException{
		try{
		c = new DBConnector();
		conn = c.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Test
	public void testSaveListOfItems(){
		//add list to database
	
	}
	
	@After
	public void tearDown(){
		//delete test tables
		//close connection
	}
	}

}
