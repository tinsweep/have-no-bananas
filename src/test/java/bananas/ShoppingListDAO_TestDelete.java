package test.java.bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.bananas.DAOUtils;
import main.java.bananas.DBConnector;
import main.java.bananas.ShoppingList;
import main.java.bananas.ShoppingListDAO;

import org.junit.Before;
import org.junit.Test;

public class ShoppingListDAO_TestDelete {
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private ShoppingListDAO dao;
	private Statement st;
	private String testTable = "WalMart";
	private ShoppingList li;
	private Logger logger = Logger.getLogger("Have No Bananas Log");
	
	
	@Before public void setUp(){
	con = DBConnector.getConnection(con);
	dao = new ShoppingListDAO();
	//create shoppingList objects to test saveList method
	//Set up a shoppingList to test
	li = new ShoppingList(testTable);
	//save empty list to be deleted
	dao.saveListOfItems(li);
	
	}
	
	@Test
	public void testDeleteList(){
		dao.deleteList(testTable);
		Boolean isTable;
		try{
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, testTable, null);
			isTable = rs.next();
			assertFalse(isTable);	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			DAOUtils.closeResultSet(rs);
		}
	}
	@Test
	public void test_That_DeleteList_Drops_Table_Name_from_ShoppingListNames(){
		dao.deleteList(testTable);
		try{
			String query = "SELECT * FROM ShoppingListNames";
			st = con.createStatement();
			rs = st.executeQuery(query);
			rs.next();
			String tableName = rs.getString(1);
			System.out.println(tableName);
			assertFalse(tableName == "WalMart");
		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.log(Level.WARNING, "There was a problem Dropping from the shopping list names table." + e1.getErrorCode() + e1.getMessage() );
			System.out.println("There was a problem Dropping from the shopping list names table." + e1.getMessage() + e1.getSQLState() + e1.getSQLState());
		}finally{
		DAOUtils.closeResultSet(rs);
		DAOUtils.closeStatement(st);
		}
	}
}
