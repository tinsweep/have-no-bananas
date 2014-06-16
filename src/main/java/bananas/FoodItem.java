package bananas;

/**
 * Created by Jen on 6/9/2014.
 */
public class FoodItem extends Item {

    private String name;
    private String category;

    //@param String FoodItem name
    public FoodItem(String s){
        name = s;
    }

    //@param String FoodItem name, String FoodItem category
    public FoodItem(String n, String c){
        name = n;
        category = c;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String c) {
        category = c;
    }

    public String getCategory() {
        return category;
    }
}
