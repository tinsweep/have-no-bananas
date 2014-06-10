package bananas;

/**
 * Created by Jen on 6/9/2014.
 */
public interface ListOfItems {
    public void addItem(ListItem item);
    public void removeItem(ListItem item);
    public void clearList();
    //public void saveList(); //Undecided if return a success indicator
    public String getName();
}
