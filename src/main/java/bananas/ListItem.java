package bananas;

/**
 * Created by Jen on 6/9/2014.
 */
public interface ListItem {
    public String getName();
    public void setQuantity(Double); //Double instead of int for 1.25 lbs
    public Double getQuantity();
    public void setUnit(String unit);
    public String getUnit();
    public void setPrice(Double price);
    public Double getPrice();
    public void setCategory(String category);
    public String getCategory();
}
