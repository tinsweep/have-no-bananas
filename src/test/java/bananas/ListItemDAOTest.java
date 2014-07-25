package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ListItemDAOTest {
	private FoodItem item;
	private ListItem listItem;
	private ListItemDAO LiDAO;
	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private Boolean tableCreated;
	private DAOUtils daoUtil = new DAOUtils();

	@Before
	public void setUp() throws SQLException{
		item = new FoodItem("Apple", "Produce");
		listItem = new ListItem.CreateListItem(item).quantity(1.0).unit("Unit").price(1.25).create();
		LiDAO = new ListItemDAO(daoUtil);
	}
	
	@Test
	public void testTableCreation(){
		LiDAO.createFoodItemTable();

		try {
			con = daoUtil.getConnection();
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, "LISTITEMS", null);
			tableCreated = rs.next();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		assertTrue(tableCreated);
	}

	@Test
	public void testThatItemIsCounted(){
		LiDAO.saveListItem(listItem, "SampleList");
		LiDAO.saveListItem(listItem, "SampleList");
		LiDAO.saveListItem(listItem, "SampleList");
		//Item count should be incremented to 3
		Integer itemCount = null;
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("SELECT * FROM listItems WHERE ItemName = ?");
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
	public void testCreateTable() throws DAOException, SQLException{
		//to avoid error in tear down
		LiDAO.saveListItem(listItem, "SampleList");
		DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
		ListItemDAO exDAO = new ListItemDAO(mockUtil);
		Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();

		exDAO.createFoodItemTable(); 
		fail("Expected Exception not thrown");
	}
	
	@Test(expected= DAOException.class)
	public void testSaveListItemEX() throws SQLException{
		//to avoid error in tear down
		LiDAO.saveListItem(listItem, "SampleList");
		DAOUtils mockUtil = Mockito.mock(DAOUtils.class);
		ListItemDAO exDAO = new ListItemDAO(mockUtil);
		Mockito.doThrow(new SQLException()).when(mockUtil).getConnection();

		exDAO.saveListItem(listItem, "SampleList");
		fail("Expected Exception not thrown");
	}

	@After
	public void tearDown(){
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("DROP TABLE listItems");
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
