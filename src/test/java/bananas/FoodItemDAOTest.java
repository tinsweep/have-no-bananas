package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FoodItemDAOTest {
	private FoodItem item;
	private FoodItemDAO fDAO;
	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private Boolean tableCreated;
	private DAOUtils daoUtil = new DAOUtils();
	 
	@Before 
	public void setUp() throws SQLException{
		item = new FoodItem("Apple", "Produce");
		fDAO = new FoodItemDAO(daoUtil);
	}
	@Test
	public void testTableCreation(){
		fDAO.createFoodItemTable();

		try {
			con = daoUtil.getConnection();
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, "FOODITEMS", null);
			tableCreated = rs.next();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		assertTrue(tableCreated);
	}

	@Test
	public void testThatItemIsCounted(){
		fDAO.saveFoodItem(item, "SampleList");
		fDAO.saveFoodItem(item, "SampleList");
		fDAO.saveFoodItem(item, "SampleList");
		//Item count should be incremented to 3
		Integer itemCount = null;
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("SELECT * FROM foodItems WHERE ItemName = ?");
			ps.setString(1, item.getName());
			rs = ps.executeQuery();
			rs.next();
			itemCount = rs.getInt(3);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		assertEquals(itemCount, (Integer)3);
	}
	
	@Test(expected = DAOException.class)
	public void testCreateTableEX() throws SQLException{
		//to avoid error in tear down
		fDAO.saveFoodItem(item, "SampleList");
		DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
		FoodItemDAO exDAO = new FoodItemDAO(mockUtil);
		Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
		
		exDAO.createFoodItemTable();
		fail("Expected Exception not thrown");
	}
	
	
	@Test
	public void testGetAllFoodItems(){
		fDAO.saveFoodItem(item, "SampleList");
		FoodItem bread = new FoodItem("bread", "baked");
		fDAO.saveFoodItem(bread, "SampleList");
		ArrayList<FoodItem> allItems = fDAO.getAllFoodItems();
		FoodItem savedItem = allItems.get(0);
		FoodItem savedItem2 = allItems.get(1);
		assertTrue(savedItem2.getName().equals("bread"));
		assertTrue(savedItem.getName().equals("Apple"));
	}
	@Test(expected = DAOException.class)
	public void testSaveListEX() throws SQLException{
		//to avoid error in tear down
		fDAO.saveFoodItem(item, "SampleList");
		DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
		FoodItemDAO exDAO = new FoodItemDAO(mockUtil);
		Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();
		
		exDAO.saveFoodItem(item, "SampleList");;
		fail("Expected Exception not thrown");
	}
	
	@After
	public void tearDown(){
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("DROP TABLE foodItems");
			ps.execute();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
