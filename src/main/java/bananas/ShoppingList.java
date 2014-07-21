package bananas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jen on 6/22/2014.
 */
public class ShoppingList implements ListOfItems {

    private String name;
    private ArrayList<ListItem> itemsList = new ArrayList<ListItem>(10);
    private HashMap<String, Integer> itemLookup = new HashMap<String, Integer>(10);
    private ShoppingListDAO dao = new ShoppingListDAO();
    private FoodItemDAO foodDAO = new FoodItemDAO();

    public ShoppingList(String inputName){
        name = inputName;
    }

    @Override
    public void addItem(ListItem item) {
        String name;
        int idx = -1;

        //Add item to itemsList
        itemsList.add(item);

        //Add (name, idx) to itemLookup
        name = item.getName();
        idx = itemsList.indexOf(item);
        addHashItem(name, idx);

    }

    //Indicates whether an ListItem of a given name exists on the ShoppingList
    public boolean itemExistsByName(String itemName){
        return itemLookup.containsKey(itemName);
    }

    @Override
    public ListItem getItem(String itemName) {
        int idx = -1;

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx < 0){
            return null;
        }
        else {
            return itemsList.get(idx);
        }
    }

    public void removeItem(ListItem item){
        int idx = -1;
        String itemName = item.getName();

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx >= 0){
            itemsList.remove(idx);
            removeHashItemByName(itemName);
            resetHashMap(idx);
        }

        item = null;
    }

    public void removeItemByName(String itemName) {
        int idx = -1;

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx >= 0){
            removeItem(itemsList.get(idx));
        }
    }

    @Override
    public void clearList() {
        itemsList.clear();
        itemLookup.clear();
    }

    @Override
    public void setName(String inputName) {
        name = inputName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<ListItem> getList(){
        return itemsList;
    }

    @Override
    public void addList(ArrayList<ListItem> items) {
        itemsList.addAll(items);
        ListItem newItem;
        String name;
        int idx = 0;
        int addIdx;
        int maxIdx = itemsList.size() - 1;

        while (idx <= maxIdx){
            newItem = items.get(idx);
            name = newItem.getName();
            addIdx = itemsList.indexOf(newItem);
            addHashItem(name, addIdx);
            newItem = null; //Clear out newItem.
            idx++;
        }
    }

    private void addHashItem(String name, int idx){
        if (!name.isEmpty()){
            itemLookup.put(name, idx);
        }
    }

    private void removeHashItemByName(String name){
        if (!name.isEmpty()){
            itemLookup.remove(name);
        }
    }

    private void resetHashMap(int idx){
        int counter = idx;
        int maxIdx  = itemsList.size() - 1;

        while (counter <= maxIdx){
            itemLookup.put(itemsList.get(counter).getName(), counter);
            counter++;
        }
    }

    //Methods to call DAO Layer
    
    public void saveShoppingListToDB(){
    	dao.saveListOfItems(this);
    	for(ListItem item : itemsList){
    		foodDAO.saveFoodItem(item.getFoodItem(), this.getName());
    	}
    }
    
    public void updateShoppingListinDB(){
    	dao.updateList(this);
    }
    
    public ListOfItems getShoppingListFromDB(){
    	return dao.getListOfItems(this.getName());
    }
    
    public void deleteShoppingListFromDB(){
    	dao.deleteList(this.getName());
    }
    
    public ArrayList<ListOfItems> getAllShoppingListsFromDB(){
    	return dao.getAllShoppingLists();
    }
    //@Override
    //Checks key attributes and listItems to see if input ShoppingList is the same
    //(equivalent) this object.
    public boolean equals(ShoppingList inputShoppingList){
        boolean isItEqual = true;
        boolean sameName = false;
        boolean sameSize = false;
        boolean sameItems = false;
        boolean keepChecking = true;

        while (keepChecking) {
            sameName = checkShoppingListName(inputShoppingList);
            if (!sameName){
                isItEqual = false;
                keepChecking = false;
            }

            sameSize = checkItemsListSize(inputShoppingList);
            if (!sameSize) {
                isItEqual = false;
                keepChecking = false;
            }

            sameItems = checkShoppingListItems(inputShoppingList);
            if (!sameItems){
                isItEqual = false;
            }
            keepChecking = false; //Done with all tests, stop checking.
        }

        return isItEqual;
    }


    public int hashCode() {
        int result = 17;
        int itemsListResult = 0;

        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getList().size();

        //Add the hashCode of each itemsList in the ShoppingList
        for (int i=0; i < itemsList.size(); i++){
            itemsListResult = itemsListResult + itemsList.get(i).hashCode();
        }

        result = 31 * result + itemsListResult;

        return  result;

    }

    private boolean checkShoppingListName(ShoppingList inputShoppingList){
        String inputName;
        boolean sameName = false;

        inputName = inputShoppingList.getName();
        sameName = name.equals(inputName);

        return sameName;

    }
    //Checks the number of items in the inputShoppingList to the number of items in this object's list.
    private boolean checkItemsListSize(ShoppingList inputShoppingList){

        boolean sameSize = (inputShoppingList.getList().size() == itemsList.size());
        return  sameSize;

    }

    //Checks if each list has the same items; order doesn't matter.
    private boolean checkShoppingListItems(ShoppingList inputShoppingList){
        boolean sameItems = false;
        boolean keepChecking = true;
        int currItemIdx = 0;
        int lastItemIdx = itemsList.size() - 1;
        ListItem compItem;
        ListItem currItem;
        String currName;

        while ((currItemIdx <= lastItemIdx) && (keepChecking)){
            currItem = itemsList.get(currItemIdx);
            currName = currItem.getName();

            //Check if there's an item by the same name
            sameItems = inputShoppingList.itemExistsByName(currName);
            keepChecking = sameItems;

            currItemIdx++;

        }

        return  sameItems;
    }
}
