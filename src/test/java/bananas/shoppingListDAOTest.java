package test.java.bananas;
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
import java.util.List;

import main.java.bananas.DAOUtils;
import main.java.bananas.DBConnector;
import main.java.bananas.FoodItem;
import main.java.bananas.ListItem;
import main.java.bananas.ListOfItems;
import main.java.bananas.ShoppingList;
import main.java.bananas.ShoppingListDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class shoppingListDAOTest {
		private Connection con;
		private ResultSet rs;
		private PreparedStatement ps;
		private ShoppingListDAO dao;
		private Statement st;
		private String testTable = "WalMart";
		private ListOfItems li;
		private ListOfItems testAgainstList;
		private ListItem item3;
		
		@Before
		public void init(){
			//open database connection
			con = DAOUtils.getConnection(con);
			//instantiate shoppingList Data Access Object
			dao = new ShoppingListDAO();
			//create shoppingList objects to test saveList method
			//Set up a shoppingList to test
			li = new ShoppingList(testTable);
			testAgainstList = new ShoppingList("WalMart");
			
			//create list items
			FoodItem foodItem = new FoodItem("Bacon");
			foodItem.setCategory("meat");
			ListItem item = new ListItem.CreateListItem(foodItem).quantity(1.0).unit("Unit").price(1.25).create();
			item.setCategory("meat");
			item.setName("Bacon");
			
			FoodItem foodItem2 = new FoodItem("Bread");
			foodItem2.setCategory("baked goods");
			ListItem item2 = new ListItem.CreateListItem(foodItem2).quantity(1.0).unit("Unit").price(2.50).create();
			item2.setCategory("baked goods");
			item2.setName("Bread");
			//create third item to test adding an item to a list (item only to be added to li
			
			FoodItem foodItem3 = new FoodItem("Cheese");
			foodItem3.setCategory("category");
			item3 = new ListItem.CreateListItem(foodItem3).quantity(1.0).unit("Unit").price(2.00).create();
			item3.setCategory("category");
			item3.setName("Cheese");

			
			//add the two items in DIFFERENT ORDER
			li.addItem(item);
			li.addItem(item2);
			testAgainstList.addItem(item2);
			testAgainstList.addItem(item);
		
		}

		@Test
		public void testCreateTables(){
			//Statement to test if table exists
			String query = "SELECT * FROM ShoppingListNames";
			String namesTableIsCreated = null;
			//result set to hold return of query
			ResultSet rs2;
			//create the table named "WalMart"
			dao.createShoppingListTable(testTable);
			
			Boolean isTable = null;
			/*****************************Act********************************/		
			try {
				DatabaseMetaData metadata = con.getMetaData();
				rs = metadata.getTables(null, null, "WALMART", null);
				//if isTable true, the table for shopping list object, WalMart, was created
				isTable = rs.next();
				st = con.createStatement();
				//select all from ShoppingListNames
				rs2 = st.executeQuery(query);
				rs2.next();
				//set namesTableIsCreated to first value in second result set
				namesTableIsCreated = rs2.getString(1);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*****************************Assert********************************/
			assertTrue(isTable);
			//Assert that the Test Tables's name was entered into the table for shoppinglist names
			assertEquals(testTable, namesTableIsCreated);
		}

		
		@Test 
		public void testSaveListOfItems(){
			dao.saveListOfItems(li);
			ListOfItems liTest = dao.getListOfItems(testTable);
			Boolean isSame = li.equals(liTest);
			Boolean isSameOutOfOrder = li.equals(testAgainstList);
			assertTrue(isSameOutOfOrder);
			assertTrue(isSame);		
		}
			
		
		@Test
		public void testGetListOfItems(){
			dao.saveListOfItems(li);
			ListOfItems loi = dao.getListOfItems(testTable);
			Boolean isSame = li.equals(loi);
			assertTrue(isSame);
		}
		
		@Test
		public void testAddItemToList(){
			dao.saveListOfItems(li);
			dao.addItemToList(item3, testTable);
			ListOfItems loi = dao.getListOfItems(testTable);
			Boolean isAdded = item3.equals(loi.getList().get(2));
			assertTrue(isAdded);	
		}
		
		
		@After
		public void tearDown(){
			try {
				//this throws an exception from the deleteList test because the table has already been deleted
				//needs to be moved to another test case
				con = DAOUtils.getConnection(con);
				ps = con.prepareStatement("DROP TABLE " + testTable);
				ps.execute();
				ps = con.prepareStatement("DROP TABLE ShoppingListNames");
				ps.execute();


				//close the connection
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				DAOUtils.closePrepared(ps);
				DAOUtils.closeConn(con);
			}
		}
	}