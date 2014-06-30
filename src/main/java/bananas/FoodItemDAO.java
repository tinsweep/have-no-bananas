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
	private Logger logger = Logger.getLogger("Have No Bananas Log");
	
	public void createFoodItemTable(){
		try {
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS FoodItems "
					+ "(Item_Name VARCHAR(50), List_Name VARCHAR(50), ItemCount INT SET DEFAULT 1");
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
	public void saveFoodItem(FoodItem item, String listName){
		try {
			String query = "SELECT * FROM FoodItems WHERE ItemName = " + item.getName();
			String insert = "INSERT INTO FoodItems VALUES(?, ?, ?)";
			st = con.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()){
				ps = con.prepareStatement(insert);
				ps.setString(1, item.getName());
				ps.setString(2, listName);
				ps.setInt(3, rs.getInt(3) + 1);
				ps.execute();
			}else{
				ps = con.prepareStatement(insert);
				ps.setString(1, item.getName());
				ps.setString(2, listName);
				ps.execute();
			}
				

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());			
		}
	}

}
