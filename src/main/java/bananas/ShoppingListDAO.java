package main.java.bananas;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ShoppingListDAO implements BananasDAO {
	private Connection con;
	private Connection conSave;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	private Logger logger = Logger.getLogger("Have No Bananas Log");
	
	private void createShoppingListNamesTable(String listName)throws DAOException{
		con = DBConnector.getConnection(con);
		try {
			String table = "ShoppingListNames";
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(ListName VARCHAR(50))");
			ps.execute();
			//call the method to add the shopping list name to the ShoppingListNames table
			ps = con.prepareStatement("INSERT INTO ShoppingListNames (ListName) VALUES(?)");
			ps.setString(1, listName);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override 
	public void createShoppingListTable(String listName) throws DAOException{
		//open connection
		con = DBConnector.getConnection(con);
		//create a table to hold the names of all shopping lists
		if(!(listName instanceof String))
			throw new DAOException("Shopping list must be named with a word");
		
		
		try {
			//Create table if it doesn't already exist
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS "
					+ listName + "(Name VARCHAR(25), Quantity DOUBLE, Price DOUBLE, "
					+ "Unit VARCHAR(25), Category VARCHAR(50))");
			ps.execute();	
			createShoppingListNamesTable(listName);	
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void saveListOfItems(ListOfItems listToSave) throws DAOException{
		//open connection
		con = DBConnector.getConnection(con);
		//create a table by the name of list if it doesn't already exist
		//the name of the list will be added to a table holding a name of all lists
		createShoppingListTable(listToSave.getName());
		
		//get the list of items from the shopping list
		//iterate over list items in the list
		for(ListItem li : listToSave.getList()){
			//get items name, unit, category, price, and quantity
			String name = li.getName();
			String unit = li.getUnit();
			String cat = li.getCategory();
			Double price = li.getPrice();
			Double qty = li.getQuantity();
			
			try {
				//create SQL statement to insert each items values into a seperate row in the table
				ps = con.prepareStatement("INSERT INTO " + listToSave.getName() + " "
						+ "(Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
				ps.setString(1, name);
				ps.setDouble(2, qty);
				ps.setDouble(3, price);
				ps.setString(4, unit);
				ps.setString(5, cat);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "There was a problem saving the shopping list: " + e.getMessage() + e.getSQLState());
				throw new DAOException(e);
			}finally{
				//DAOUtils.closePrepared(ps);
				//DAOUtils.closeConn(con);

			}
		}//end for-each
	}//end saveListOfItems
	

	@Override
	public ListOfItems getListOfItems(String listName) throws DAOException{	// gets a shopping list from the database
		//open a connection
		con = DBConnector.getConnection(con);
		//create a new shopping list to copy values to and return
		ListOfItems result = new ShoppingList(listName);
		ListItem item;
		FoodItem foodItem;
		try {
			//get all rows from the table by the list name
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
				item.setName(name);

				result.addItem(item);

		}//end while
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "There was an error getting the list from the database: " + e.getMessage() + e.getSQLState());
			throw new DAOException(e);
		}finally{
			DAOUtils.closeConn(con);
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return result;
	}
	@Override
	public void addItemToList(ListItem itemToAdd, String listName) throws DAOException{
		//add a food item to a list with the given name
		con = DBConnector.getConnection(con);
		//deconstruct the item passed and insert it into the table specified
		try {
			ps = con.prepareStatement("INSERT INTO " + listName + " (Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
			ps.setString(1, itemToAdd.getFoodItem().getName() );
			ps.setDouble(2, itemToAdd.getQuantity() );
			ps.setDouble(3, itemToAdd.getPrice() );
			ps.setString(4, itemToAdd.getUnit());
			ps.setString(5, itemToAdd.getFoodItem().getCategory());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);		
		}finally{
			DAOUtils.closeConn(con);
			DAOUtils.closePrepared(ps);
		}

	}
	@Override
	public void deleteList(String listName) throws DAOException{
		//open connection
		con = DBConnector.getConnection(con);
		try {
			//delete the specified table from the database
			ps = con.prepareStatement("DROP TABLE " + listName);
			ps.execute();
			ps = con.prepareStatement("DELETE FROM ShoppingListNames WHERE ListName = 'WalMart'");
			ps.execute();			
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			DAOUtils.closeConn(con);
			DAOUtils.closePrepared(ps);
		}
		}

	@Override
	public void updateList(ListOfItems listToUpdate) {
		// TODO Auto-generated method stub
		
	}

}
