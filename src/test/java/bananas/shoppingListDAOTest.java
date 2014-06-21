package bananas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class shoppingListDAOTest {

	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	private shoppingListDAO dao;
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testCreateTable(){
		/*****************************Arrange********************************/
		//create table 
		con = DBConnector.getConnection(con);
		dao = new shoppingListDAO();
		String t = "TestTable";
		Boolean isTable = null;
		dao.createTable(t);
		String query = "Select * FROM TestTable";
		try {
			ps = con.prepareStatement("INSERT INTO TestTable VALUES(1, 1.25, 'Unit', 'Food')");
			ps.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*****************************Act********************************/		
		try {
			DatabaseMetaData metadata = con.getMetaData();
			rs = metadata.getTables(null, null, "TESTTABLE", null);
			//st = con.createStatement();
			//rs = st.executeQuery(query);
			isTable = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*****************************Assert********************************/
		assertTrue(isTable);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testGetAllLists(){
		/*****************************Arrange********************************/
		con = DBConnector.getConnection(con);
		dao = new shoppingListDAO();
		List<ListOfItems> checkAllLists = new ArrayList<ListOfItems>();
		Integer tblCnt = 1;
		while(tblCnt <= 3){
		
		String t = "TestTable" + tblCnt.toString();
		dao.createTable(t);
		try {
			ps = con.prepareStatement("INSERT INTO " + t + " VALUES(1, 1.25, 'Unit', 'Food')");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
		tblCnt++;
		
		}//end while
		
		
		/*****************************Act************************************/	
		
		//get tables created
		checkAllLists = dao.getAllLists();
		int chk = checkAllLists.size();
		/*****************************Assert*********************************/	
		System.out.println(checkAllLists.size());
		assertTrue(3 == checkAllLists.size());
	}
	
	@Test 
	public void testSaveListOfItems(){
		/*****************************Arrange********************************/		
		/*****************************Act************************************/		
		/*****************************Assert*********************************/
	}
	
	@Test
	public void testGetListOfItems(){
		/*****************************Arrange********************************/		
		/*****************************Act************************************/		
		/*****************************Assert*********************************/
	}
	
	@Test
	public void testAddItemToList(){
		/*****************************Arrange********************************/		
		/*****************************Act************************************/		
		/*****************************Assert*********************************/
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
		/*****************************Act************************************/		
		/*****************************Assert*********************************/
	}
	

}
