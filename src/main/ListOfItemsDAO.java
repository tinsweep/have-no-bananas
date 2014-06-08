package main;

import java.util.List;

public interface ListOfItemsDAO {
	
	public List<ListOfItems> getAllLists();
	public void saveListOfItems(ListOfItems g);
	public List<ListItem> getListOfItems(String s);
	public void addItemToList(FoodItem f);
}
