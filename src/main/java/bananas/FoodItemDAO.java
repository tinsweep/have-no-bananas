package main.java.bananas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class FoodItemDAO {
	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	String insert = "INSERT INTO FoodItems VALUES(?, ?, ?)";
	private Logger logger = Logger.getLogger("Have No Bananas Log");
	
	public void createFoodItemTable(){
		try {
			con = DAOUtils.getConnection(con);
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS foodItems (ItemName VARCHAR(50) NOT NULL, "
					+ "ListName VARCHAR(50), "
					+ "ItemCount INT DEFAULT 0, "
					+ "PRIMARY KEY (ItemName))");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("There was a problem creating the food items table", e);
		}finally{
		}
	}
	public void saveFoodItem(FoodItem item, String listName){
		try {
			con = DAOUtils.getConnection(con);

			createFoodItemTable();			
			
			ps = con.prepareStatement("INSERT INTO foodItems VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE ItemCount = ItemCount + 1");
			ps.setString(1, item.getName());
			ps.setString(2, listName);
			ps.setInt(3, 1);
			//ps.setString(4, listName);
			//ps.setString(5, "ItemCount = ItemCount + 1");
					
			//HERE IS THE PROBLEM, ITEM COUNT IS RESETTING TO ZERO ON MERGE
			//ps.setInt(3, 1);
			ps.execute();
			//
			//ps = con.prepareStatement("UPDATE foodItems SET ItemCount = ItemCount + 1 WHERE ItemName = ?");
			//ps.setString(1, item.getName());
			//ps.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());			
		}finally{
		}
	}

}
