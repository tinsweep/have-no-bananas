package main;

import java.util.List;

public class ShoppingListDAO implements ListOfItemsDAO{
	
	@Override
	public List<ListOfItems>getAllLists(){
		//search through database for any shopping lists and
		//return them as an Arraylist
		
	}
	public void saveListOfItems(ListOfItems shpList){
		//create connection and add object to the database
	}
	
	@Override
	public List<ListItem> getListOfItems(String name){
		//return shopping list with name s
	}
	
	@Override
	public List<ListItem> addItemToList(FoodItem foodItem){
		
		
	}

	

}
