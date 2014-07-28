package bananas;

/**
 * Created by Bryan on 6/7/2014.
 **/
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShoppingListDAO implements BananasDAO {
	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	private DAOUtils daoUtil;

	public ShoppingListDAO(){
		if(daoUtil == null){
			daoUtil = new DAOUtils();
		}
	}

	public ShoppingListDAO(DAOUtils daoUtil){
		this.daoUtil = daoUtil;
	}
	
	/*Helper method to check if table exists*/
	public boolean tableExists(String tableName){
		ResultSet returned;
		
		boolean isTable = false;
		try {
			con = daoUtil.getConnection();
			DatabaseMetaData metadata = con.getMetaData();
			returned = metadata.getTables(null, null, tableName, null);
			isTable = returned.next();
		}catch(SQLException e){
			return isTable;
		}
		return isTable;
	}
	/*
	 *Method to create the table that holds the names of all current shopping lists. It is called from saveListOfItems
	 *and in turn, calls the method to addToShoppingListNames
	 * */
	private void createTableOfShoppingListNames(String listName){ 

		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS ShoppingListNames (ListName VARCHAR(25) NOT NULL, PRIMARY KEY (ListName))");
			ps.execute();

		} catch (SQLException e) {
			throw new DAOException("There was a problem adding the shopping list to the names table.", e);
		}finally{
			daoUtil.closePrepared(ps);
		}
		addToShoppingListNames(listName);
	}
	/*
	 * Method to add shopping list name to ShoppingListNames table
	 * */
	private void addToShoppingListNames(String listName){

		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("INSERT INTO ShoppingListNames (ListName) VALUES(?) ON DUPLICATE KEY UPDATE ListName = ListName");
			ps.setString(1, listName);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("There was a problem adding the Shopping list to the table of Shopping List names.", e);
		}finally{
			//daoUtil.closePrepared(ps);
		}

	}

	@Override 
	/*Creates a table for the shopping list and ads the lists name to a table 
	 *referencing all current lists*/
	public void createShoppingListTable(String listName){
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + listName
					+ "(Name VARCHAR(25) NOT NULL, Quantity DOUBLE, Price DOUBLE, "
					+ "Unit VARCHAR(25), Category VARCHAR(50),"
					+ "PRIMARY KEY (Name))");
			ps.execute();	

		} catch (SQLException e) {
			throw new DAOException("There was a problem creating the shopping list table.", e);
		}finally{
			//daoUtil.closePrepared(ps);
			//daoUtil.closeConnection(con);
		}
		createTableOfShoppingListNames(listName);
	}

	@Override 
	/*
	 * Calls createShoppingListTable, then iterates over each list item and inserts a row for each item in the 
	 * array list. If the list item being inserted already exists, it will be replaced
	 * */
	public void saveListOfItems(ListOfItems listToSave){
		createShoppingListTable(listToSave.getName());
		for(ListItem li : listToSave.getList()){
			String name = li.getName();
			String unit = li.getUnit();
			String cat = li.getCategory();
			Double price = li.getPrice();
			Double qty = li.getQuantity();

			try {
				con = daoUtil.getConnection();
				ps = con.prepareStatement("INSERT INTO " + listToSave.getName() + " "
						+ "(Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?) ON DUPLICATE KEY UPDATE QUANTITY = QUANTITY");
				ps.setString(1, name);
				ps.setDouble(2, qty);
				ps.setDouble(3, price);
				ps.setString(4, unit);
				ps.setString(5, cat);
				ps.executeUpdate();

			} catch (SQLException e) {
				throw new DAOException("There was a problem saving the shopping list.", e);
			}finally{
				daoUtil.closePrepared(ps);
				daoUtil.closeConn(con);
				//NOTE: this is causing errors, need connection pool
			}
		}//end for-each
	}//end saveListOfItems


	@Override
	/*
	 * Returns a Shopping list from the database with the given name
	 * */

	public ListOfItems getListOfItems(String listName){	
		ListOfItems result = new ShoppingList(listName);
		ListItem item;
		FoodItem foodItem;
		boolean notTable = tableExists(listName);
		if(!notTable){
			throw new DAOException("This table does not exist");
		}
		try {
			con = daoUtil.getConnection();
			String query = "SELECT * FROM " + listName;
			st = con.createStatement();
			rs = st.executeQuery(query);
		while(rs.next()){
				String name = rs.getString(1);

				Double qty = rs.getDouble(2);
				Double price = rs.getDouble(3);
				String unit = rs.getString(4);
				String cat = rs.getString(5);

				foodItem = new FoodItem(name);
				foodItem.setCategory(cat);
				item = new ListItem.CreateListItem(foodItem).quantity(qty).unit(unit).price(price).create();
				item.setCategory(cat);

				result.addItem(item);

		}//end while

		} catch (SQLException e) {
			throw new DAOException("There was an error getting the list from the database.", e);
		}finally{
			daoUtil.closeResultSet(rs);
			daoUtil.closeStatement(st);
			daoUtil.closeConn(con);
		}
		return result;
	}
	/*
	 * Adds a list item to a shopping list with the specified name
	 */
	@Override
	public void addItemToList(ListItem itemToAdd, String listName){
		boolean notTable = tableExists(listName);
		if(!notTable){
			throw new DAOException("This table does not exist");
		}
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("INSERT INTO " + listName + " (Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
			ps.setString(1, itemToAdd.getName() );
			ps.setDouble(2, itemToAdd.getQuantity() );
			ps.setDouble(3, itemToAdd.getPrice() );
			ps.setString(4, itemToAdd.getUnit());
			ps.setString(5, itemToAdd.getCategory());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);		
		}finally{
			daoUtil.closeConn(con);
			daoUtil.closePrepared(ps);
		}

	}
	
	public void deleteItemFromList(String itemName, String listName){
		boolean notTable = tableExists(listName);
		if(!notTable){
			throw new DAOException("This table does not exist");
		}
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("DELETE FROM " + listName + " WHERE Name= ?");
			ps.setString(1, itemName);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);		
		}finally{
			daoUtil.closeConn(con);
			daoUtil.closePrepared(ps);
		}
		
	}
	/*
	 * Deletes the specified shopping list and deletes its name from the table of shopping list names
	 */
	@Override
	public void deleteList(String listName){
		boolean notTable = tableExists(listName);
		if(!notTable){
			throw new DAOException("This table does not exist");
		}
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("DROP TABLE " + listName);
			ps.execute();
			ps = con.prepareStatement("DELETE FROM ShoppingListNames WHERE ListName = ?");
			ps.setString(1, listName);
			ps.executeUpdate();			
		} catch (SQLException e) {
			throw new DAOException("There was a problem deleting the shopping list.", e);
		}finally{
			daoUtil.closeConn(con);
			daoUtil.closePrepared(ps);
		}
		}
	/*
	 * queries the table of shopping list names and returns an ArrayList containing all current shopping lists
	 * */
	@Override
	public ArrayList<ListOfItems> getAllShoppingLists(){
		ArrayList<ListOfItems> allLists = new ArrayList<ListOfItems>();
		ResultSet rs2= null;
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("SELECT * FROM ShoppingListNames");
			rs2 = ps.executeQuery();
			while(rs2.next()){
				String tableName = rs2.getString(1);
				ListOfItems item = getListOfItems(tableName);
				allLists.add(item);
			}
		} catch (SQLException e) {
			throw new DAOException("There was a problem retrieving the shopping lists from the database", e);
		}finally{
			daoUtil.closePrepared(ps);
			daoUtil.closeResultSet(rs2);
			daoUtil.closeConn(con);
		}
		return allLists;
	}

	@Override
	public void updateList(ListOfItems listToUpdate){		
		boolean notTable = tableExists(listToUpdate.getName());
		if(!notTable){
			throw new DAOException("This table does not exist");
		}
			try {
				con = daoUtil.getConnection();
				//iterate over array list and update each 
				for(ListItem item : listToUpdate.getList()){
				ps = con.prepareStatement("INSERT INTO " + listToUpdate.getName() +  "(Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?) ON DUPLICATE KEY UPDATE Quantity = ?, Price = ?, Unit = ?, Category = ?");
				ps.setString(1, item.getName() );
				ps.setDouble(2, item.getQuantity() );
				ps.setDouble(3, item.getPrice() );
				ps.setString(4, item.getUnit());
				ps.setString(5, item.getCategory());
				ps.setDouble(6, item.getQuantity());
				ps.setDouble(7, item.getPrice());
				ps.setString(8, item.getUnit());
				ps.setString(9, item.getCategory());
				ps.executeUpdate();
				}//end for-each
			} catch (SQLException e) {
				throw new DAOException("There was a problem updating the shopping list.", e);
			}finally{
				daoUtil.closePrepared(ps);
				daoUtil.closeConn(con);
			}
	}//end updateList
	

}
