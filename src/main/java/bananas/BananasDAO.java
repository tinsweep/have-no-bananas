package bananas;

import java.util.ArrayList;

/**
 * Created by Bryan on 6/18/2014.
 **/

public interface BananasDAO {
	public void createShoppingListTable(String listName);
	public void saveListOfItems(ListOfItems listToSave);
	public ListOfItems getListOfItems(String listName);
	public void addItemToList(ListItem itemToAdd, String listName);
	public void deleteList(String listName);
	public ArrayList<ListOfItems> getAllShoppingLists();
}
