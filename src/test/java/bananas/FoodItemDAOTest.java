package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
import main.java.bananas.DAOException;
import main.java.bananas.DAOUtils;
import main.java.bananas.DBConnector;
import main.java.bananas.FoodItem;
import main.java.bananas.FoodItemDAO;
*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FoodItemDAOTest {
	private FoodItem item;
	private FoodItemDAO fDAO;
	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private Boolean tableCreated;
	
	@Before
	public void setUp(){
		con = DAOUtils.getConnection(con);
		item = new FoodItem("Apple", "Produce");
		fDAO = new FoodItemDAO();
	}
	@Test
	public void testTableCreation(){
		fDAO.createFoodItemTable();
		
		try {
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
	
	@After
	public void tearDown(){
		con = DBConnector.getConnection(con);
		try {
			ps = con.prepareStatement("DROP TABLE foodItems");
			ps.execute();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
