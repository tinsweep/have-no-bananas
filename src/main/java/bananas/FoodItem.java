package main.java.bananas;

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
    /* Author: Bryan Thetford
     * Date: 6/23/14
  */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

    /* Author: Bryan Thetford
     * Date: 6/23/14
  */
	   @Override
	    public boolean equals(Object item){
		   if(this == item)
			   return true;
		   if(item == null)
			   return false;
		   if(!(item instanceof FoodItem))
	    		return false;
		   FoodItem itemToCompare = (FoodItem) item;
		   if(name.equals(itemToCompare.name) && category.equals(itemToCompare.category))
			   return true;
		   else
			   return false;
	    }
    
}




