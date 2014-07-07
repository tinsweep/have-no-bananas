package bananas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodItemDAO {
	private Connection con;
	private PreparedStatement ps;
	String insert = "INSERT INTO FoodItems VALUES(?, ?, ?)";
	
	/**
	 * Creates the table to hold food items added to a list
	 */
	public void createFoodItemTable(){
		try {
			con = DAOUtils.getConnection(con);
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS foodItems (ItemName VARCHAR(50) NOT NULL, "
					+ "ListName VARCHAR(50), "
					+ "ItemCount INT DEFAULT 0, "
					+ "PRIMARY KEY (ItemName))");
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException("There was a problem creating the food items table", e);
		}finally{
		}
	}
	/*
	 * Calls the create table method, then adds the food item to the list
	 * If the item is already on the list the counter is incremented by one
	 * */
	public void saveFoodItem(FoodItem item, String listName){
		try {
			con = DAOUtils.getConnection(con);

			createFoodItemTable();			
			
			ps = con.prepareStatement("INSERT INTO foodItems VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE ItemCount = ItemCount + 1");
			ps.setString(1, item.getName());
			ps.setString(2, listName);
			ps.setInt(3, 1);
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException("There was a problem saving the shoppping list.", e);			
		}finally{
		}
	}

}
