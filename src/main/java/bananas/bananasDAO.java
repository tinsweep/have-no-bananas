package bananas;
/**
 * Created by Bryan on 6/18/2014.
 **/
import java.util.List;
public interface bananasDAO {

		public List<ListOfItems> getAllLists();
		public void saveListOfItems(ListOfItems listToSave);
		public List<ListItem> getListOfItems(String listName);
		public void addItemToList(FoodItem itemToAdd, String listName);
		public void updateList(String listName);
		public void deleteList(String listName);
	}

}
