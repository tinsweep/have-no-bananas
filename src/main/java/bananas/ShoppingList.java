package bananas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jen on 6/22/2014.
 */
public class ShoppingList implements ListOfItems {

    private String name;
    private ArrayList<ListItem> itemsList = new ArrayList<ListItem>(10);
    private HashMap<String, Integer> itemLookup = new HashMap<String, Integer>(10);

    public ShoppingList(String n){
        name = n;
    }

    @Override
    public void addItem(ListItem item) {
        String name;
        int idx = -1;

        //Add item to itemsList
        itemsList.add(item);

        //Add (name, idx) to itemLookup
        name = item.getName();
        idx = itemsList.indexOf(item);
        addHashItem(name, idx);

    }

    @Override
    public ListItem getItem(String itemName) {
        int idx = -1;

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx < 0){
            return null;
        }
        else {
            return itemsList.get(idx);
        }
    }

    public void removeItem(ListItem item){
        int idx = -1;
        String itemName = item.getName();

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx >= 0){
            itemsList.remove(idx);
            removeHashItemByName(itemName);
            resetHashMap(idx);
        }

        item = null;
    }

    public void removeItemByName(String itemName) {
        int idx = -1;

        if (itemLookup.containsKey(itemName)) {
            idx = itemLookup.get(itemName);
        }
        if (idx >= 0){
            removeItem(itemsList.get(idx));
        }
    }

    @Override
    public void clearList() {
        itemsList.clear();
        itemLookup.clear();
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<ListItem> getList(){
        return itemsList;
    }

    @Override
    public void addList(ArrayList<ListItem> items) {
        itemsList.addAll(items);
        ListItem newItem;
        String name;
        int idx;

        Iterator<ListItem> itr = itemsList.iterator();
        while (itr.hasNext()){
            newItem = itr.next();
            name = newItem.getName();
            idx = itemsList.indexOf(newItem);
            addHashItem(name, idx);
            newItem = null; //Clear out newItem.
        }
    }

    private void addHashItem(String name, int idx){
        if (!name.isEmpty()) itemLookup.put(name, idx);
    }

    private void removeHashItemByName(String name){
        if (!name.isEmpty()) itemLookup.remove(name);
    }

    private void resetHashMap(int idx){
        int counter = idx;
        int maxIdx  = itemsList.size() - 1;

        while (counter <= maxIdx){
            itemLookup.put(itemsList.get(counter).getName(), counter);
            counter++;
        }
    }
}
