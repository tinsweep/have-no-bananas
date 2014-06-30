package main.java.bananas;

/**
 * Created by Jen on 6/9/2014.
 */
public class ListItem extends Item {

    private String name;
    private Double quantity;
    private String category;
    private String unit;
    private Double price;
    private FoodItem foodItem;

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

        public CreateListItem name(String val){
            name = val;
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
    
    public void setName(String name){
    	this.name = name;
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

    

    public FoodItem getFoodItem(){
    	return foodItem;
    }
    /* Author: Bryan Thetford
     * Date: 6/23/14
  */
    @Override
    public boolean equals(Object obj){
    	if(this == obj)
    		return true;
    	//if arg obj is null return false
    	if(obj == null)
    		return false;
    	if(!(obj instanceof ListItem))
    		return false;
    	//cast obj to ListItem
    	ListItem li = (ListItem)obj;
    	//if any fields are not equal return true
    	if(!(foodItem.equals(li.foodItem)))
    		return false;
    	if(!(name.equals(li.name)))
    		return false;
    	if(!(price.equals(li.price)))
    		return false;
    	if(!(quantity.equals(li.quantity)))
    		return false;
    	if(!(unit.equals(li.unit)))
    		return false;
    	if(!(category.equals(li.category)))
    		return false;
    	//else
    	return true;
    }   
    /* Author: Bryan Thetford
     * Date: 6/23/14
  */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((foodItem == null) ? 0 : foodItem.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
}