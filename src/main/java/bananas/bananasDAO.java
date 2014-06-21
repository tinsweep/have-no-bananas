package bananas;

import java.util.List;

public interface bananasDAO {
	public void createTable(String listName);
	public List<ListOfItems> getAllLists();
	public void saveListOfItems(ListOfItems listToSave);
	public ListOfItems getListOfItems(String listName);
	public void addItemToList(ListItem itemToAdd, String listName);
	public void updateList(ListOfItems listToUpdate);
	public void deleteList(String listName);

}
