package bananas;

/**
 *
 * todo be sure to javadoc all classes
 *
 * Created by Jen on 6/9/2014.
 */
public class FoodItem implements FoodInfo {

    private String name;

    public FoodItem(String s) {
        name = s;
    }

    /*
        public void FoodItem(String s){

            name = s;
        }
      */
    public void setName(String name) {

    }

    // todo be sure to javadoc all methods
    public String getName() {
        return name;
    }

    public void setCategory(String category) {

    }

    public String getCategory() {
        return null;
    }
}
