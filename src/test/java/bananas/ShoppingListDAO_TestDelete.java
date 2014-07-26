package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.bananas.DAOException;
import main.java.bananas.DAOUtils;
import main.java.bananas.DBConnector;
import main.java.bananas.ShoppingList;
import main.java.bananas.ShoppingListDAO;

import org.junit.Before;
import org.junit.Test;

public class ShoppingListDAO_TestDelete {
	
	private Connection con;
	private ResultSet rs;
	private ShoppingListDAO dao;
	private Statement st;
	private String testTable = "WalMart";
	private ShoppingList li;	
	
	@Before public void setUp(){
	con = DBConnector.getConnection(con);
	dao = new ShoppingListDAO();
	li = new ShoppingList(testTable);
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
		} catch (SQLException e) {
			throw new DAOException("There was a problem deleting the shopping list from the database.", e);
		}finally{
			DAOUtils.closeResultSet(rs);
		}
	}
	@Test
	public void test_That_DeleteList_Drops_Table_Name_from_ShoppingListNames(){
		
		dao.deleteList(testTable);
		Integer numRows = null;
		try{
			String query = "SELECT COUNT(*) FROM ShoppingListNames";
			st = con.createStatement();
			rs = st.executeQuery(query);
			rs.next();
			numRows = rs.getInt(1);
			
		} catch (SQLException e) {
			throw new DAOException("There was a problem deleting from the shopping list names table.", e);
		}finally{
		DAOUtils.closeResultSet(rs);
		DAOUtils.closeStatement(st);
		}
		assertTrue(numRows == 0);
	}
}
