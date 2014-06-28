package bananas;

/**
 * Created by Jen on 6/9/2014.
 * FoodItem class describes any item that could be added to a ShoppingList.
 * Created as a class separate from ListItem to make future enhancements easier,
 * such as storing a list of all FoodItems created by the user.
 */
public class FoodItem extends Item {

    private String name;
    private String category;

    //@param String FoodItem name
    public FoodItem(String inputName){
        name = inputName;
    }

    //@param String FoodItem name, String FoodItem category
    public FoodItem(String inputName, String inputCategory){
        name = inputName;
        category = inputCategory;
    }

    public void setName(String inputName) {
        name = inputName;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String inputCategory) {
        category = inputCategory;
    }

    public String getCategory() {
        return category;
    }
}
