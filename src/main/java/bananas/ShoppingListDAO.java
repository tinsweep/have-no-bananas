package main.java.bananas;

/**
 * Created by Bryan on 6/7/2014.
 **/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShoppingListDAO implements BananasDAO {
	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	
	private void createTableOfShoppingListNames(String listName)throws DAOException{
		con = DAOUtils.getConnection(con);
		try {
			String table = "ShoppingListNames";
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + "(ListName VARCHAR(50))");
			ps.execute();
			ps = con.prepareStatement("INSERT INTO ShoppingListNames (ListName) VALUES(?)");
			ps.setString(1, listName);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("There was a problem adding the shopping list to the names table.", e);
		}
	}

	@Override 
	/*Creates a table for the shopping list and ads the lists name to a table 
	 *referencing all current lists*/
	public void createShoppingListTable(String listName) throws DAOException{
		con = DAOUtils.getConnection(con);
		try {
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + listName
					+ "(Name VARCHAR(25), Quantity DOUBLE, Price DOUBLE, "
					+ "Unit VARCHAR(25), Category VARCHAR(50))");
			ps.execute();	
			createTableOfShoppingListNames(listName);	
		} catch (SQLException e) {
			throw new DAOException("There was a problem creating the shopping list table.", e);
		}
	}
	
	@Override
	/*
	 * Calls createShoppingListTable, then iterates over each list item and inserts a row for each item in the 
	 * array list
	 * */
	public void saveListOfItems(ListOfItems listToSave) throws DAOException{
		con = DAOUtils.getConnection(con);
		createShoppingListTable(listToSave.getName());
		for(ListItem li : listToSave.getList()){
			String name = li.getName();
			String unit = li.getUnit();
			String cat = li.getCategory();
			Double price = li.getPrice();
			Double qty = li.getQuantity();
			
			try {
				ps = con.prepareStatement("INSERT INTO " + listToSave.getName() + " "
						+ "(Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
				ps.setString(1, name);
				ps.setDouble(2, qty);
				ps.setDouble(3, price);
				ps.setString(4, unit);
				ps.setString(5, cat);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				throw new DAOException("There was a problem saving the shopping list.", e);
			}finally{
				//DAOUtils.closePrepared(ps);
				//DAOUtils.closeConn(con);

			}
		}//end for-each
	}//end saveListOfItems
	

	@Override
	/*
	 * Returns a Shopping list form the database
	 * */
	
	public ListOfItems getListOfItems(String listName) throws DAOException{	
		con = DAOUtils.getConnection(con);
		ListOfItems result = new ShoppingList(listName);
		ListItem item;
		FoodItem foodItem;
		try {
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
			DAOUtils.closeConn(con);
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return result;
	}
	@Override
	public void addItemToList(ListItem itemToAdd, String listName) throws DAOException{
		con = DAOUtils.getConnection(con);
		try {
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
			DAOUtils.closeConn(con);
			DAOUtils.closePrepared(ps);
		}

	}
	@Override
	public void deleteList(String listName) throws DAOException{
		con = DBConnector.getConnection(con);
		try {
			ps = con.prepareStatement("DROP TABLE " + listName);
			ps.execute();
			ps = con.prepareStatement("DELETE FROM ShoppingListNames WHERE ListName = ?");
			ps.setString(1, listName);
			ps.executeUpdate();			
		} catch (SQLException e) {
			throw new DAOException("There was a problem deleting the shopping list.", e);
		}finally{
			DAOUtils.closeConn(con);
			DAOUtils.closePrepared(ps);
		}
		}

	@Override
	public void updateList(ListOfItems listToUpdate) {
		//unimplemented method
	}

}
