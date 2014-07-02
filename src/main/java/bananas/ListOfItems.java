package bananas;

import  java.util.ArrayList;
/**
 * Created by Jen on 6/9/2014.
 */
public interface ListOfItems {
    public void addItem(ListItem item);
    public ListItem getItem(String itemName);
    public void removeItem(ListItem item);
    public void clearList();
    //public void saveList(); //Undecided if return a success indicator
    public void setName(String listName);
    public String getName();
    public ArrayList<ListItem> getList();
    public void addList(ArrayList<ListItem> items);
}
