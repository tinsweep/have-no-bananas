package bananas;

/**
 * Created by Jen on 6/15/2014.
 * Item is the base class for FoodItem and ListItem.
 */
abstract class Item {

    //abstract void setName(String n);
    abstract String getName();
    abstract void setCategory(String inputCategory);
    abstract String getCategory();
}
