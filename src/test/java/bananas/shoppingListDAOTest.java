package bananas;
/**
 * Created by Bryan on 6/7/2014.
 **/
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
import main.java.bananas.DAOException;
import main.java.bananas.DAOUtils;
import main.java.bananas.FoodItem;
import main.java.bananas.ListItem;
import main.java.bananas.ListOfItems;
import main.java.bananas.ShoppingList;
import main.java.bananas.ShoppingListDAO;
*/
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class shoppingListDAOTest {
		private Connection con;
		private ResultSet returned;
		private PreparedStatement ps;
		private ShoppingListDAO dao;
		private Statement st;
		private String testTable = "WalMart";
		private ListOfItems listToTest;
		private ListOfItems testAgainstList;
		private ListItem item3;
		
		@Before
		public void init(){
			con = DAOUtils.getConnection(con);
			dao = new ShoppingListDAO();
			listToTest = new ShoppingList(testTable);
			testAgainstList = new ShoppingList("WalMart");
			
			FoodItem foodItem = new FoodItem("Bacon");
			foodItem.setCategory("meat");
			ListItem item = new ListItem.CreateListItem(foodItem).quantity(1.0).unit("Unit").price(1.25).create();
			item.setCategory("meat");
			
			FoodItem foodItem2 = new FoodItem("Bread");
			foodItem2.setCategory("baked goods");
			ListItem item2 = new ListItem.CreateListItem(foodItem2).quantity(1.0).unit("Unit").price(2.50).create();
			item2.setCategory("baked goods");
			
			FoodItem foodItem3 = new FoodItem("Cheese");
			foodItem3.setCategory("category");
			item3 = new ListItem.CreateListItem(foodItem3).quantity(1.0).unit("Unit").price(2.00).create();
			item3.setCategory("category");

			listToTest.addItem(item);
			listToTest.addItem(item2);
			testAgainstList.addItem(item);
			testAgainstList.addItem(item2);
			testAgainstList.addItem(item3);
		
		}
		/*
		 * Tests that table for the shopping list was created and that the shopping list was added
		 * to the table holding the names of all current lists
		 * */
		@Test
		public void testCreateTables(){
			String query = "SELECT * FROM ShoppingListNames";
			String namesTableIsCreated = null;
			ResultSet namesTableResult;
			dao.createShoppingListTable(testTable);
			
			Boolean isTable = null;
			try {
				DatabaseMetaData metadata = con.getMetaData();
				returned = metadata.getTables(null, null, "WALMART", null);
				isTable = returned.next();
				st = con.createStatement();
				namesTableResult = st.executeQuery(query);
				namesTableResult.next();
				namesTableIsCreated = namesTableResult.getString(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException(e);
			}
			
			assertTrue(isTable);
			assertEquals(testTable, namesTableIsCreated);
		}

		@Test 
		public void testSaveListOfItems(){
			dao.saveListOfItems(listToTest);
			ListOfItems liTest = dao.getListOfItems(testTable);
			ShoppingList castLi = (ShoppingList)listToTest;
			ShoppingList castReturn = (ShoppingList) liTest;
			Boolean isSame = castLi.equals(castReturn);
			assertTrue(isSame);		
		}
			
		@Test
		public void testGetListOfItems(){
			dao.saveListOfItems(listToTest);
			ListOfItems loi = dao.getListOfItems(testTable);
			//cast from ListOfItems to ShoppingList
			ShoppingList castLoi = (ShoppingList) loi;
			ShoppingList castLi = (ShoppingList) listToTest;
			Boolean isSame = castLoi.equals(castLi);
			assertTrue(isSame);
		}
		
		@Test
		public void testAddItemToList(){
			dao.saveListOfItems(listToTest);
			dao.addItemToList(item3, testTable);
			ListOfItems loi = dao.getListOfItems(testTable);
			//cast from ListOfItems to ShoppingLists
			ShoppingList castLoi = (ShoppingList) loi;
			ShoppingList castTestAgainst = (ShoppingList)testAgainstList;
			Boolean isAddedToList = castLoi.equals(castTestAgainst);
			assertTrue(isAddedToList);	
		}
		
		@After
		public void tearDown(){
			try {
				con = DAOUtils.getConnection(con);
				ps = con.prepareStatement("DROP TABLE " + testTable);
				ps.execute();
				ps = con.prepareStatement("DROP TABLE ShoppingListNames");
				ps.execute();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
			finally{
				DAOUtils.closePrepared(ps);
				DAOUtils.closeConn(con);
			}
		}
	}