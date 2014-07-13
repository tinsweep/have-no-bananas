package gui;

import bananas.FoodItem;
import bananas.ListItem;
import bananas.ShoppingList;

import java.util.ArrayList;

/**
 * Data container to carry data throughout runtime
 */
public class DataContainer {

    private ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
    private ArrayList<ListItem> listItems = new ArrayList<ListItem>();
    private ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();

    public DataContainer() {};

    public DataContainer(ArrayList<ShoppingList> shoppingLists,
                         ArrayList<ListItem> listItems,
                         ArrayList<FoodItem> foodItems) {

        this.shoppingLists = shoppingLists;
        this.listItems = listItems;
        this.foodItems = foodItems;
    }

    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public ArrayList<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(ArrayList<ListItem> listItems) {
        this.listItems = listItems;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}
