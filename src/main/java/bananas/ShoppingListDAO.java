package bananas;
/**
 * Created by Bryan on 6/7/2014.
 **/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ShoppingListDAO implements bananasDAO{
	private DBConnector c = new DBConnector();
	private Connection conn = c.getConnection();
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	
	
	@Override
	public List<ListOfItems> getAllLists() {
		//get all shopping lists not of type "recipe" or "pantry"
		return null;
	}
	@Override
	public void saveListOfItems(ListOfItems listToSave) {
		// save a list
		
	}
	@Override
	public List<ListItem> getListOfItems(String listName) {
		// get a list of food items from the database
		return null;
	}
	@Override
	public void addItemToList(FoodItem itemToAdd, String listName) {
		//add a food item to a list with the given name
		
	}
	@Override
	public void updateList(String listName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteList(String listName) {
		// TODO Auto-generated method stub
		
	}
	
	

	

}
