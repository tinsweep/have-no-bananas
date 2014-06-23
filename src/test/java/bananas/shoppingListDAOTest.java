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
import java.util.List;

import org.junit.Test;

public class shoppingListDAOTest {

	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private ShoppingListDAO dao;
	private Statement st;
	
	
	@Test
	public void testCreateTables(){
		/*****************************Arrange********************************/
		//create table 
		con = DBConnector.getConnection(con);
		dao = new ShoppingListDAO();
		String t = "TestTable";
		String query = "SELECT * FROM ShoppingListNames";
		String namesTableIsCreated = null;
		Boolean isTable = null;
		ResultSet rs2;
		dao.createShoppingListTable(t);
		

		try {
			ps = con.prepareStatement("INSERT INTO TestTable VALUES('Bacon', 1, 1.25, 'Unit', 'Food')");
			ps.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*****************************Act********************************/		
		try {
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, "TESTTABLE", null);
			isTable = rs.next();
			st = con.createStatement();
			rs2 = st.executeQuery(query);
			rs2.next();
			namesTableIsCreated = rs2.getString(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*****************************Assert********************************/
		assertTrue(isTable);
		assertEquals(t, namesTableIsCreated);
		try {
			ps = con.prepareStatement("DROP TABLE " + t);
			ps.execute();
			ps = con.prepareStatement("Drop TABLE ShoppinListNames");
			//close the connection
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	@Test 
	public void testSaveListOfItems(){
		/*****************************Arrange********************************/
		con = DBConnector.getConnection(con);
		dao = new ShoppingListDAO();
		Boolean areEqual = null;
		ShoppingList li = new ShoppingList("WalMart");
		li.setName("WalMart");
		
		//create list items
		FoodItem foodItem = new FoodItem("Bacon");
		foodItem.setCategory("Category");
		ListItem item = new ListItem.CreateListItem(foodItem).quantity(1.0).unit("Unit").price(1.25).create();
		item.setCategory("Category");
		item.setName("Bacon");
		li.addItem(item);
		
		FoodItem foodItem2 = new FoodItem("Bread");
		foodItem2.setCategory("Category");
		ListItem item2 = new ListItem.CreateListItem(foodItem2).quantity(1.0).unit("Unit").price(2.50).create();
		item2.setCategory("Category");
		item2.setName("Bread");
		li.addItem(item2);

		
		/*****************************Act************************************/	
		dao.saveListOfItems(li);
		
		//create new list to test against
		ShoppingList liTest = new ShoppingList("ListTest");
		//set test list to equal the saved list
		liTest = dao.getListOfItems("WalMart");
		//get the items from the saved list
		item = liTest.getItem("Bacon");
		item2 = liTest.getItem("Bread");
		
		//recreate the items saved to compare against
		String name = liTest.getList().get(0).getName();
		String unit = liTest.getList().get(0).getUnit();
		String cat = liTest.getList().get(0).getCategory();
		Double qty = liTest.getList().get(0).getQuantity();
		Double price = liTest.getList().get(0).getPrice();
		
		String name2 = liTest.getList().get(1).getName();
		String unit2 = liTest.getList().get(1).getUnit();
		String cat2 = liTest.getList().get(1).getCategory();
		Double qty2 = liTest.getList().get(1).getQuantity();
		Double price2 = liTest.getList().get(1).getPrice();
		
		if(name.equals("Bacon") && qty.equals(1.0) && price.equals(1.25) && unit.equals("Unit") 
		  && cat.equals("Category") && name2.equals("Bread") && qty2.equals(1.0) && price2.equals(2.50)
		  && unit2.equals("Unit") && cat2.equals("Category"))
			areEqual = true;
		else
			areEqual = false;
		/*****************************Assert*********************************/
		
		assertTrue(areEqual);
		//*****************************Clean up*****************************/
		try {
			//drop test table
			ps = con.prepareStatement("DROP TABLE " + li.getName());
			ps.execute();
			//close the connection
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetListOfItems(){
		/*****************************Arrange********************************/
		con = DBConnector.getConnection(con);
		dao = new ShoppingListDAO();
		String t = "testGetList";
		String query1 = "INSERT INTO " + t + " VALUES('Bacon', 1.0, 1.25, 'Unit', 'Category')";
		String query2 = "INSERT INTO " + t + " VALUES('Bread', 1.0, 2.50, 'Unit', 'Category')";
		Boolean areEqual = null;

		ShoppingList loi;
		
		
		loi = new ShoppingList(t);
		
		//create a table
		dao.createShoppingListTable(t);
		try {
			ps = con.prepareStatement(query1);
			ps.execute();
			ps = con.prepareStatement(query2);
			ps.execute();
		/*****************************Act************************************/
			loi = dao.getListOfItems(t);
			
			String tname = loi.getList().get(0).getName();
			Double tqty = loi.getList().get(0).getQuantity();
			Double tprice = loi.getList().get(0).getPrice();
			String tunit = loi.getList().get(0).getUnit();
			String tcat = loi.getList().get(0).getCategory();
			
			String tname2 = loi.getList().get(1).getName();
			Double tqty2 = loi.getList().get(1).getQuantity();
			Double tprice2 = loi.getList().get(1).getPrice();
			String tunit2 = loi.getList().get(1).getUnit();
			String tcat2 = loi.getList().get(1).getCategory();
			
			if(tname.equals("Bacon") && tqty.equals(1.0) && tprice.equals(1.25) && tunit.equals("Unit") 
			   && tcat.equals("Category") && tname2.equals("Bread") && tqty2.equals(1.0) && tprice2.equals(2.50)
			   && tunit2.equals("Unit") && tcat2.equals("Category"))
				areEqual = true;
			else
				areEqual = false;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*****************************Assert*********************************/
		
		assertTrue(areEqual);
		/****************************Clean up********************************/
		try {
			ps = con.prepareStatement("DROP TABLE " + t);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddItemToList(){
		/*****************************Arrange********************************/	
		con = DBConnector.getConnection(con);
		dao = new ShoppingListDAO();
		String list = "TestList";
		Boolean areEqual = null;
		FoodItem food = new FoodItem("Cheese", "Perishables");
		ListItem item = new ListItem.CreateListItem(food).quantity(1.0).unit("Unit").price(2.00).create();
		item.setCategory(food.getCategory());
		
		dao.createShoppingListTable(list);
		
		/*****************************Act************************************/
		dao.addItemToList(item, list);
		ShoppingList sList= new ShoppingList(list);
		sList = dao.getListOfItems(list);
		List<ListItem> loi = sList.getList();
		String name = loi.get(0).getName();
		String unit = loi.get(0).getUnit();
		String cat = loi.get(0).getCategory();
		Double price = loi.get(0).getPrice();
		Double qty = loi.get(0).getQuantity();
		
		if(name.equals("Cheese") && qty.equals(1.0) && price.equals(2.00) && unit.equals("Unit") 
				   && cat.equals("Perishables"))
			areEqual = true;
		else
			areEqual = false;
		
		/*****************************Assert*********************************/
		assertTrue(areEqual);
		
		/**************************clean up**********************************/
		try {
			ps = con.prepareStatement("DROP TABLE " + list);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateList(){
		/*****************************Arrange********************************/		
		/*****************************Act************************************/		
		/*****************************Assert*********************************/
	}
	
	@Test
	public void testDeleteList(){
		/*****************************Arrange********************************/	
		//create table 
		con = DBConnector.getConnection(con);
		dao = new ShoppingListDAO();
		String t = "TestTable";
		Boolean isTable = null;
		dao.createShoppingListTable(t);

		try {
			ps = con.prepareStatement("INSERT INTO TestTable VALUES('Bacon', 1, 1.25, 'Unit', 'Food')");
			ps.execute();

			
			/*****************************Act********************************/
			dao.deleteList(t);
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, "TESTTABLE", null);
			//st = con.createStatement();
			//rs = st.executeQuery(query);
			isTable = rs.next();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		/*****************************Assert********************************/
		assertFalse(isTable);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
