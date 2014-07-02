package bananas;

import org.junit.Before;
import org.junit.Test;

import  java.util.ArrayList;

import static junit.framework.Assert.*;

/**
 * Created by Jen on 6/22/2014.
 */
public class ShoppingListTest {

    private ArrayList<ListItem> testShoppingList = new ArrayList<ListItem>(5);
    private ArrayList<ListItem> rtnShoppingList = new ArrayList<ListItem>(5);
    private String name;

    private ArrayList<ListItem> internalShoppingList = new ArrayList<ListItem>(3);
    private ArrayList<ListItem> externalShoppingList = new ArrayList<ListItem>(4);
    private String internalName;
    private String externalName;

    private Double lettuceQty = 1.0;  private String lettuceUnit = "head";       private Double lettucePrice = 0.89;
    private Double yogurtQty = 5.0;   private String yogurtUnit  = "container";  private Double yogurtPrice = 1.29;
    private Double breadQty = 1.0;    private String breadUnit   = "loaf";       private Double breadPrice  = 2.79;
    private Double shampooQty = 1.0;  private String shampooUnit = "bottle";     private Double shampooPrice = 4.79;
    private Double ryeWhiskeyQty = 1.0; private String ryeWhiskeyUnit = "bottle";  private Double ryeRyeWhiskeyPrice = 13.0;

    private FoodItem lettuce    = new FoodItem("lettuce", "produce");
    private FoodItem yogurt     = new FoodItem("yogurt", "dairy");
    private FoodItem bread      = new FoodItem("bread", "grocery");
    private FoodItem shampoo    = new FoodItem("shampoo", "health and beauty");
    private FoodItem ryeWhiskey = new FoodItem("Old Overholt", "liquor");

    private ListItem lettuceListed = new ListItem.CreateListItem(lettuce).quantity(lettuceQty).unit(lettuceUnit).price(lettucePrice).create();
    private ListItem yogurtListed = new ListItem.CreateListItem(yogurt).quantity(yogurtQty).unit(yogurtUnit).price(yogurtPrice).create();
    private ListItem breadListed = new ListItem.CreateListItem(bread).quantity(breadQty).unit(breadUnit).price(breadPrice).create();
    private ListItem shampooListed = new ListItem.CreateListItem(shampoo).quantity(shampooQty).unit(shampooUnit).price(shampooPrice).create();
    private ListItem ryeWhiskeyListed = new ListItem.CreateListItem(ryeWhiskey).quantity(ryeWhiskeyQty).unit(ryeWhiskeyUnit).price(ryeRyeWhiskeyPrice).create();

    @Before
    public void setup(){
        testShoppingList.add(lettuceListed);
        testShoppingList.add(yogurtListed);
        testShoppingList.add(breadListed);
        testShoppingList.add(shampooListed);
        testShoppingList.add(ryeWhiskeyListed);

        name = "TestList";

        internalShoppingList.add(lettuceListed);
        internalShoppingList.add(yogurtListed);
        internalShoppingList.add(breadListed);

        internalName = "Equals List";
    }

    @Test
    public void testAddAndGetList(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addList(testShoppingList);
        assertTrue("Testing addList and getList - ", testShoppingList.equals(shoppingList.getList()));
        shoppingList = null;
    }

    @Test
    public void testGetItem(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addItem(lettuceListed);
        assertTrue("Testing getItem - ", lettuceListed.equals(shoppingList.getItem(lettuceListed.getName())));
        shoppingList = null;
    }

    @Test
    public void testAddItem(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addItem(lettuceListed);
        assertTrue("Testing addItem and getItem - ", lettuceListed.equals(shoppingList.getItem("lettuce")));
        shoppingList = null;
    }

    @Test
    public void testGetItemAfterAddList(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addList(testShoppingList);
        assertTrue("Testing get correct item after adding a list of items at once -", breadListed.equals(shoppingList.getItem("bread")));
        shoppingList = null;
    }

    @Test
    public void testGetItemFail(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addList(testShoppingList);
        assertFalse("Testing that getItem returns null when item not in list - ", breadListed.equals(shoppingList.getItem("socks")));
        shoppingList = null;
    }

    @Test
    public void testSetAndGetName(){
        ShoppingList shoppingList = new ShoppingList("Test");
        shoppingList.addList(testShoppingList);
        shoppingList.setName(name);
        assertTrue("Testing setName and getName - ", name.equals(shoppingList.getName()));
        shoppingList = null;
    }

    @Test
    public void testSetAndGetNameFail(){
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.addList(testShoppingList);
        shoppingList.setName("Stop and Shop");
        assertFalse("Testing setName and getName using a false result - ", name.equals(shoppingList.getName()));
        shoppingList = null;
    }

    @Test
    public void testRemoveItemByName(){
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.addList(testShoppingList);
        shoppingList.removeItemByName(yogurtListed.getName());
        assertFalse("Testing removeItemByName: check item not there - ", yogurtListed.equals(shoppingList.getItem(yogurtListed.getName())));
        assertTrue("Testing removeItemByName: check item before still there - ",lettuceListed.equals(shoppingList.getItem("lettuce")));
        assertTrue("Testing removeItemByName: check item after still there - ",breadListed.equals(shoppingList.getItem("bread")));
        shoppingList = null;
    }

    @Test
    public void testRemoveItem(){
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.addList(testShoppingList);
        shoppingList.removeItem(yogurtListed);
        assertFalse("Testing removeItem: check item not there - ", yogurtListed.equals(shoppingList.getItem(yogurtListed.getName())));
        assertTrue("Testing removeItem: check item befpre still there - ",lettuceListed.equals(shoppingList.getItem("lettuce")));
        assertTrue("Testing removeItem: check item after still there - ",breadListed.equals(shoppingList.getItem("bread")));
        shoppingList = null;
    }

    @Test
    public void testClearList(){
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.addList(testShoppingList);
        shoppingList.clearList();
        assertFalse("Testing clearList: check item not there - ", yogurtListed.equals(shoppingList.getItem(yogurtListed.getName())));
        shoppingList = null;
    }

    @Test
    public void testItemExistsByName(){
        ShoppingList shoppingList = new ShoppingList(name);
        shoppingList.addList(testShoppingList);
        assertTrue("Testing positive itemExistsByName - ", shoppingList.itemExistsByName(lettuceListed.getName()));
        assertFalse("Testing negative itemExistsByName - ", shoppingList.itemExistsByName("baloney"));
    }

    @Test
    public void testEqualsTrue(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName);
        compList.addList(internalShoppingList);

        assertTrue("Testing equals when lists are equal - ", shoppingList.equals(compList));
        shoppingList = null;
        compList = null;

    }

    @Test
    public void testEqualsDiffName(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList("nope");
        compList.addList(internalShoppingList);

        assertFalse("Testing equals when names are diff - ", shoppingList.equals(compList));
        shoppingList = null;
        compList = null;

    }

    @Test
    public void testEqualsDiffNumberOfItems(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        externalShoppingList.add(lettuceListed);
        ShoppingList compList = new ShoppingList(internalName);
        compList.addList(externalShoppingList);

        assertFalse("Testing equals is false if list have diff # of items - ", shoppingList.equals(compList));
        shoppingList = null;
        compList = null;

    }

    @Test
    public void testEqualsDiffItems(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        externalShoppingList.add(lettuceListed);
        externalShoppingList.add(yogurtListed);
        externalShoppingList.add(shampooListed);
        ShoppingList compList = new ShoppingList(internalName);
        compList.addList(externalShoppingList);

        assertFalse("Testing equals is false when items are diff - ", shoppingList.equals(compList));
        shoppingList = null;
        compList = null;

    }

    @Test
    public void testHashCodeListsEqual(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName);
        compList.addList(internalShoppingList);

        assertEquals("Testing hashCode when lists are equal - ",shoppingList.hashCode(), compList.hashCode());
        shoppingList = null;
        compList = null;
    }

    @Test
    public void testHashCodeListsNameDiff(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName + "-TEST");
        compList.addList(internalShoppingList);

        assertFalse("Testing hashCode when lists are equal but name is not - ",shoppingList.hashCode() == compList.hashCode());
        shoppingList = null;
        compList = null;
    }

    @Test
    public void testHashCodeListsDiff(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addList(internalShoppingList);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName);
        compList.addList(testShoppingList);

        assertFalse("Testing hashCode when names are equal but lists are not - ",shoppingList.hashCode() == compList.hashCode());
        shoppingList = null;
        compList = null;
    }

    @Test
    public void testHashCodeListDiffOrder(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addItem(lettuceListed);
        shoppingList.addItem(breadListed);
        shoppingList.addItem(yogurtListed);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName);
        compList.addItem(breadListed);
        compList.addItem(yogurtListed);
        compList.addItem(lettuceListed);

        assertEquals("Testing hashCode when lists are equal but in diff order - ",shoppingList.hashCode(), compList.hashCode());
        shoppingList = null;
        compList = null;
    }


    @Test
    public void testHashCodeListSameSizeDiffItems(){
        ShoppingList shoppingList = new ShoppingList(internalName);
        shoppingList.addItem(lettuceListed);
        shoppingList.addItem(breadListed);
        shoppingList.addItem(yogurtListed);

        //Set up ShoppingList to check against
        ShoppingList compList = new ShoppingList(internalName);
        compList.addItem(breadListed);
        compList.addItem(ryeWhiskeyListed);
        compList.addItem(lettuceListed);

        assertFalse("Testing hashCode when lists are equal size but in diff items - ",shoppingList.hashCode() == compList.hashCode());
        shoppingList = null;
        compList = null;
    }

}
