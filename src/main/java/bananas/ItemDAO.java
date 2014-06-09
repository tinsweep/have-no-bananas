package bananas;

public interface ItemDAO {
	public List<FoodItem> getAllItems();
	public FoodItem getFoodItem(String s);
	public void updateItem(FoodItem i);
	public void deleteItem(FoodItem i);

}
