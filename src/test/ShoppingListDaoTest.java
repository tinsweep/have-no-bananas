package test;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ShoppingListDaoTest {
	
	protected void setup() throws SQLException{
		try{
		Connection c = DriverManager.getConnection("org.h2.Driver");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Test
	public void testSaveListOfItems(){
		//add list to database
	}

}
