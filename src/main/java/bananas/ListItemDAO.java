package bananas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ListItemDAO {
		private Connection con;
		private PreparedStatement ps;
		String insert = "INSERT INTO listItems VALUES(?, ?, ?)";
		private DAOUtils daoUtil;
		
		public ListItemDAO(DAOUtils daoUtil){
			this.daoUtil = daoUtil;
		}
		/**
		 * Creates the table to hold food items added to a list
		 */
		public void createFoodItemTable(){
			try {
				con = daoUtil.getConnection();
				ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS listItems (ItemName VARCHAR(50) NOT NULL, "
						+ "ListName VARCHAR(50), "
						+ "ItemCount INT DEFAULT 0, "
						+ "PRIMARY KEY (ItemName))");
				ps.execute();
			} catch (SQLException e) {
				throw new DAOException("There was a problem creating the list items table", e);
			}
		}
		/*
		 * Calls the create table method, then adds the list item to the list
		 * If the item is already on the list the counter is incremented by one
		 * */
		public void saveListItem(ListItem item, String listName){
			try {
				con = daoUtil.getConnection();

				createFoodItemTable();			
				
				ps = con.prepareStatement("INSERT INTO listItems VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE ItemCount = ItemCount + 1");
				ps.setString(1, item.getName());
				ps.setString(2, listName);
				ps.setInt(3, 1);
				ps.execute();
			} catch (SQLException e) {
				throw new DAOException("There was a problem saving the to the list of ListItems.", e);			
			}
		}
}

