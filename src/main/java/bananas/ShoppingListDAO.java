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
import java.util.List;


public class ShoppingListDAO implements BananasDAO{
	private Connection con;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	
	public void createShoppingListNamesTable(String listName){
		con = DBConnector.getConnection(con);
		try {
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS ShoppingListNames(NameOfList VARCHAR(50))");
			ps.execute();
			//call the method to add the shopping list name to the ShoppingListNames table
			addShoppingListName(listName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addShoppingListName(String nameOfList){
		con = DBConnector.getConnection(con);
		try {
			ps = con.prepareStatement("INSERT INTO ShoppingListNames VALUES(?)");
			ps.setString(1, nameOfList);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override 
	public void createShoppingListTable(String listName){
		//open connection
		con = DBConnector.getConnection(con);
		try {
			//Create table if it doesn't already exist
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS "
					+ listName + "(Name VARCHAR(25), Quantity DOUBLE, Price DOUBLE, "
					+ "Unit VARCHAR(25), Category VARCHAR(50))");
			ps.execute();	
			//create a table to hold the names of all shopping lists
			createShoppingListNamesTable(listName);
			
			//close connection
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void saveListOfItems(ListOfItems listToSave) {
		//open connection
		con = DBConnector.getConnection(con);
		//create a table by the name of list
		String listName = listToSave.getName();
		createShoppingListTable(listName);
		
		//get the list of items from the shopping list
		List<ListItem> items = listToSave.getList();
		//iterate over list items in the list
		for(ListItem li : items){
			//get items name, unit, category, price, and quantity
			String name = li.getName();
			String unit = li.getUnit();
			String cat = li.getCategory();
			Double price = li.getPrice();
			Double qty = li.getQuantity();
			try {
				//create SQL statement to insert each items values into a seperate row in the table
				ps = con.prepareStatement("INSERT INTO " + listName + " (Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
				ps.setString(1, name);
				ps.setDouble(2, qty);
				ps.setDouble(3, price);
				ps.setString(4, unit);
				ps.setString(5, cat);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end for-each
	}//end saveListOfItems
	

	@Override
	public ShoppingList getListOfItems(String listName) {	// gets a list of food items from the database
		//open a connection
		con = DBConnector.getConnection(con);
		//create a new shopping list to copy values to and return
		ShoppingList result = new ShoppingList(listName);
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
			
				//close connection
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void addItemToList(ListItem itemToAdd, String listName) {
		//add a food item to a list with the given name
		con = DBConnector.getConnection(con);
		//deconstruct the item passed and insert it into the table specified
		try {
			ps = con.prepareStatement("INSERT INTO " + listName + " (Name, Quantity, Price, Unit, Category) VALUES(?, ? , ? , ? , ?)");
			ps.setString(1, itemToAdd.getName() );
			ps.setDouble(2, itemToAdd.getQuantity() );
			ps.setDouble(3, itemToAdd.getPrice() );
			ps.setString(4, itemToAdd.getUnit());
			ps.setString(5, itemToAdd.getCategory());
			ps.executeUpdate();

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void updateList(ListOfItems listToUpdate) {

		saveListOfItems(listToUpdate);

	}
	@Override
	public void deleteList(String listName) {
		//open connection
		con = DBConnector.getConnection(con);
		try {
			//delete the specified table from the database
			ps = con.prepareStatement("DROP TABLE " + listName);
			ps.execute();
			//close connection
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
