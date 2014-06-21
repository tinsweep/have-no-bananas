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


public class shoppingListDAO implements bananasDAO{
	private Connection conn;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	
	@Override 
	public void createTable(String listName){
		//open connection
		conn = DBConnector.getConnection(conn);
		try {
			//Create table if it doesn't already exist
			ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "
					+ listName + "(Quantity DOUBLE, Price DOUBLE, "
					+ "Unit VARCHAR(25), Catagory VARCHAR(50))");
			ps.execute();
			
			//close connection
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public List<ListOfItems> getAllLists() {
		//get all shopping lists not of type "recipe" or "pantry"
		//open connection
		conn = DBConnector.getConnection(conn);
		List<ListOfItems> allLists = new ArrayList<ListOfItems>();
		ListOfItems shoppingList = new shoppingList();
		
		try {
			//close connection
			DatabaseMetaData metadata = conn.getMetaData();
			rs = metadata.getTables(null, null, null, null);
			int cnt = 1;
			while(rs.next()){
				String tableName = rs.getString(cnt);
				//ps = conn.prepareStatement("Select * From " + tableName);
				shoppingList = getListOfItems(tableName);
				allLists.add(shoppingList);
				cnt++;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allLists;
	}
	@Override
	public void saveListOfItems(ListOfItems listToSave) {
		// save a list
		//open connection
		conn = DBConnector.getConnection(conn);

		try {
			//for(ListItem item : listToSave){
			//Can't iterate over interface, need concrete implementation
			//}
			
			//close connection
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public ListOfItems getListOfItems(String listName) {
		// get a list of food items from the database
		conn = DBConnector.getConnection(conn);
		shoppingList result = new shoppingList();
		ListItem item = new ListItem();
		try {
			String query = "SELECT * from " + listName;
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()){
				//Assumes column order that is not set yet
				String name = rs.getString(1);
				item.setName(name);
				Double qty = rs.getDouble(2);
				item.setQuantity(qty);
				Double price = rs.getDouble(3);
				item.setPrice(price);
				String unit = rs.getString(4);
				item.setUnit(unit);
				String cat = rs.getString(5);
				item.setCategory(cat);
				result.addItem(item);
				
				//close connection
				conn.close();

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public void addItemToList(ListItem itemToAdd, String listName) {
		//add a food item to a list with the given name
		conn = DBConnector.getConnection(conn);
		try {
			ps = conn.prepareStatement("INSERT INTO " + listName + " VALUES(" 
					+ itemToAdd.getName() 
					+ itemToAdd.getQuantity() 
					+ itemToAdd.getPrice() 
					+ itemToAdd.getUnit() 
					+ itemToAdd.getCategory());
			conn.close();
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
		conn = DBConnector.getConnection(conn);
		try {
			ps = conn.prepareStatement("DROP TABLE " + listName);
			ps.execute();
			//close connection
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
