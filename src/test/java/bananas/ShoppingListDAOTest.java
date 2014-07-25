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
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito; 


public class ShoppingListDAOTest {
		private Connection con;
		private ResultSet returned;
		private PreparedStatement ps;
		private ShoppingListDAO dao;
		private Statement st;
		private String testTable = "WalMart";
		private ListOfItems listToTest;
		private ListOfItems testAgainstList;
		private ListOfItems anotherList;
		private ListItem item3;
		private DAOUtils daoUtil = new DAOUtils();
		
		@Before
		public void init() throws SQLException{
			dao = new ShoppingListDAO(daoUtil);
			con = daoUtil.getConnection();
			
			listToTest = new ShoppingList(testTable);
			testAgainstList = new ShoppingList("WalMart");
			anotherList = new ShoppingList("HEB");
			
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
			anotherList.addItem(item);
			anotherList.addItem(item2);
		
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
			dao.createShoppingListTable(listToTest.getName());
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
		public void testSaveListOfItems() {
			dao.saveListOfItems(listToTest);
			ListOfItems liReturned = dao.getListOfItems(listToTest.getName());
			ShoppingList castLi = (ShoppingList)listToTest;
			ShoppingList castReturn = (ShoppingList) liReturned;
			Boolean isSame = castLi.equals(castReturn);			
			assertTrue(isSame);		
		}
			
		@Test
		public void testGetListOfItems() {
			dao.saveListOfItems(listToTest);
			ListOfItems loi = dao.getListOfItems(testTable);
			//cast from ListOfItems to ShoppingList
			ShoppingList castLoi = (ShoppingList) loi;
			ShoppingList castLi = (ShoppingList) listToTest;
			Boolean isSame = castLoi.equals(castLi);
			assertTrue(isSame);
		}
		
		@Test(expected = DAOException.class)
		public void testGetListFail(){
			dao.saveListOfItems(listToTest);
			dao.getListOfItems("NoSuchTable");
		}
		
		@Test
		public void testAddItemToList() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			dao.addItemToList(item3, testTable);
			ListOfItems loi = dao.getListOfItems(testTable);
			//cast from ListOfItems to ShoppingLists
			ShoppingList castLoi = (ShoppingList) loi;
			ShoppingList castTestAgainst = (ShoppingList)testAgainstList;
			Boolean isAddedToList = castLoi.equals(castTestAgainst);
			assertTrue(isAddedToList);	
		}
		
		@Test(expected = DAOException.class)
		public void testAddItemFal(){
			//to avoid error in tearDown()
			dao.saveListOfItems(listToTest);
			dao.addItemToList(item3, "NoSuchList");
		}
		
		@Test
		public void testGetAllLists() throws DAOException, SQLException{
			dao.saveListOfItems(anotherList);
			dao.saveListOfItems(listToTest);
			
			ArrayList<ListOfItems> allLists = dao.getAllShoppingLists();
			assertTrue(allLists.size() == 2);
		}

		
		@Test
		public void testMultipleSaves() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			FoodItem foodItem4 = new FoodItem("Eggs");
			foodItem4.setCategory("category");
			ListItem item4 = new ListItem.CreateListItem(foodItem4).quantity(1.0).unit("Unit").price(2.00).create();
			item4.setCategory("category");
			
			listToTest.addItem(item4);
			dao.saveListOfItems(listToTest);
			ShoppingList printList = (ShoppingList) dao.getListOfItems(listToTest.getName());
			ShoppingList testList = (ShoppingList) listToTest;
			assertTrue(testList.equals(printList));
			
			
		}
		
		@Test
		public void testUpdateList() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			listToTest.addItem(item3);
			dao.updateList(listToTest);
			ShoppingList blah = (ShoppingList) dao.getListOfItems(testTable);
			ShoppingList blahdeedah = (ShoppingList) testAgainstList;
			assertTrue(blahdeedah.equals(blah));
		}
		
		@Test(expected = DAOException.class)
		public void testUpdateFails(){
			//avoid error in tearDown()
			dao.saveListOfItems(listToTest);
			dao.updateList(anotherList);
		}
		
		@Test
		public void testDeleteList() throws DAOException, SQLException{
			ResultSet rs = null;
			dao.saveListOfItems(listToTest);
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
				daoUtil.closeResultSet(rs);
			}
			//recreate table so that tearDown method does not create error
			dao.saveListOfItems(listToTest);
		}
		
		@Test(expected = DAOException.class)
		public void testDeleteFails(){
			//avoid error in tearDown()
			dao.saveListOfItems(listToTest);
			dao.deleteList("NoSuchList");
		}
		@Test
		public void test_That_DeleteList_Drops_Table_Name_from_ShoppingListNames() throws DAOException, SQLException{
			ResultSet rs = null;
			dao.saveListOfItems(listToTest);
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
			daoUtil.closeResultSet(rs);
			daoUtil.closeStatement(st);
			}
			assertTrue(numRows == 0);
			dao.saveListOfItems(listToTest);
			
		}
		
		@Test(expected = DAOException.class)
		public void testSaveList() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ShoppingList mockShopping = Mockito.mock(ShoppingList.class);
			//a separate dao object for testing exceptions
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			exDAO.saveListOfItems(mockShopping);
		}
		
		@Test(expected = DAOException.class)
		public void testGetListEX() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			exDAO.getListOfItems(testTable);
			fail("Expected Exception not thrown");
		}
		
		@Test(expected = DAOException.class)
		public void testAddItemEX() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ListItem mockItem = Mockito.mock(ListItem.class);
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			
			exDAO.addItemToList(mockItem, testTable);
			fail("Expected Exception not thrown");
		}
		
		@Test(expected = DAOException.class)
		public void testDeleteEX() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			
			exDAO.deleteList(testTable);
			fail("Expected Exception not thrown");
		}
		
		@Test(expected = DAOException.class)
		public void testGetAllListsEX() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			
			exDAO.getAllShoppingLists();
			fail("Expected Exception not thrown");
		}
		
		@Test(expected = DAOException.class)
		public void testUpdateEX() throws DAOException, SQLException{
			dao.saveListOfItems(listToTest);
			DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
			ListOfItems mockShopping = Mockito.mock(ListOfItems.class);
			ShoppingListDAO exDAO = new ShoppingListDAO(mockUtil);
			Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
			
			exDAO.updateList(mockShopping);
			fail("Expected Exception not thrown");
		}
		
		@After
		public void tearDown(){
			try {
				con = daoUtil.getConnection();
				ps = con.prepareStatement("DROP TABLE " + testTable);
				ps.execute();
				ps = con.prepareStatement("DROP TABLE ShoppingListNames");
				ps.execute();
			
			} catch (SQLException e) {
				throw new DAOException(e);
			}
			finally{
				daoUtil.closePrepared(ps);
				daoUtil.closeConn(con);
			}
		}
		
	}