package bananas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodItemDAO {
	private Connection con;
	private PreparedStatement ps;
	String insert = "INSERT INTO FoodItems VALUES(?, ?, ?)";
	private DAOUtils daoUtil;
	private ResultSet rs;
	
	public FoodItemDAO(DAOUtils daoUtil){
		this.daoUtil = daoUtil;
	}
	/**
	 * Creates the table to hold food items added to a list
	 */
	public void createFoodItemTable(){
		
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS foodItems (ItemName VARCHAR(50) NOT NULL, "
					+ "ListName VARCHAR(50), "
					+ "ItemCount INT DEFAULT 0, "
					+ "PRIMARY KEY (ItemName))");
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException("There was a problem creating the food items table", e);
		}
	}
	/*
	 * Calls the create table method, then adds the food item to the list
	 * If the item is already on the list the counter is incremented by one
	 * */
	public void saveFoodItem(FoodItem item, String listName){
		
		createFoodItemTable();	
		try {
			con = daoUtil.getConnection();
			ps = con.prepareStatement("INSERT INTO foodItems VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE ItemCount = ItemCount + 1");
			ps.setString(1, item.getName());
			ps.setString(2, listName);
			ps.setInt(3, 1);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			throw new DAOException("There was a problem saving the shoppping list.", e);			
		}
	}
	public ArrayList<FoodItem> getAllFoodItems(){
		
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		try{
			con = daoUtil.getConnection();
			ps = con.prepareStatement("SELECT * FROM foodItems");
			rs = ps.executeQuery();
			while(rs.next()){
				String name = rs.getString("ItemName");
				Integer cnt = rs.getInt("ItemCount");		
				FoodItem item = new FoodItem(name);
				foodList.add(item);
			}
			
		}catch(SQLException e){
			throw new DAOException("There was a problem retrieving all FoodItems");
		}
		return foodList;
	}
}
