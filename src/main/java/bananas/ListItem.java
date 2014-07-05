package bananas;

/**
 * Created by Jen on 6/9/2014.
 * ListItem is a FoodItem that is on a ShoppingList.  Unlike FoodItem, ListItem has quantity, price, and unit attributes.
 */
public class ListItem extends Item {

    private String name;
    private Double quantity;
    private String category;
    private String unit;
    private Double price;
    private FoodItem foodItem;

    /*
    ListItem uses the builder method in place of a constructor with
    CreateListItem as the inner class.  This is to make it easier to create an
    object when using one of several parameter combinations.

    The required parameter is FoodItem- a ListItem is a FoodItem with additional
    attributes.  Optional parameters are name, quantity, category, unit, and
    price.  All numeric parameters are Double.

    To create a ListItem object, use the following syntax:

    ListItem listItem = new ListItem.CreateListItem(foodItem).quantity(qty).unit(itemUnit).price(itemPrice).create();

    You can leave out parameters you don't need or don't have, but you must
    begin the declaration with "ListItem.CreateListItem(FoodItem)" and end the statement with ".create();".
     */
    public static class CreateListItem {

        //Required parameter
        private final FoodItem foodItem;

        //Optional parameters
        private String name;
        private Double quantity;
        private String category;
        private String unit;
        private Double price;

        public CreateListItem(FoodItem val){
            foodItem = val;
        }

        public CreateListItem name(){
            name = foodItem.getName();
            return this;
        }

        public CreateListItem quantity(Double val){
            quantity = val;
            return this;
        }

        public CreateListItem category(String val){
            category = val;
            return this;
        }

        public CreateListItem unit(String val){
            unit = val;
            return this;
        }

        public CreateListItem price(Double val){
            price = val;
            return this;
        }

        public ListItem create(){
            return new ListItem(this);
        }
    }

    private ListItem(CreateListItem listItemData){
        foodItem = listItemData.foodItem;
        name = listItemData.name;
        quantity = listItemData.quantity;
        category = listItemData.category;
        unit = listItemData.unit;
        price = listItemData.price;
    }

    public String getName() {
        return foodItem.getName();
    }

    public void setQuantity(Double q) {
        quantity = q;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setUnit(String u) {
        unit = u;
    }

    public String getUnit() {
        return unit;
    }

    public void setPrice(Double p) {
        price = p;
    }

    public Double getPrice() {
        return price;
    }

    public void setCategory(String c) {
        category = c;
    }

    public String getCategory() {
        return category;
    }

}
